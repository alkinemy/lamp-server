package lamp.admin.domain.host.service;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.host.Host;
import lamp.admin.core.host.HostCredentials;
import lamp.admin.core.script.ScriptCommand;
import lamp.admin.core.script.ScriptExecuteCommand;
import lamp.admin.core.script.ScriptFileCreateCommand;
import lamp.admin.core.script.ScriptFileRemoveCommand;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.host.model.*;
import lamp.admin.domain.support.el.ExpressionParser;
import lamp.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class HostAgentInstallService {

	private static final char LF = '\n';
	private static final char CR = '\r';

	private ExpressionParser expressionParser = new ExpressionParser();

	@Autowired
	private AgentInstallProperties agentInstallProperties;

	@Autowired
	private HostService hostService;

	@Transactional
	public AgentInstallResult installAgent(TargetHost targetHost,
										   HostCredentials hostCredentials,
										   String agentFilePath,
										   HostConfiguration hostConfiguration) {
		// FIXME 경로별 분류 구현
		AgentInstallResult result = new AgentInstallResult();
		File installLogFile = new File(targetHost.getId() + "_" + System.currentTimeMillis() + ".log");
		log.info("installLogFile : {}", installLogFile.getAbsolutePath());
		result.setInstallLogFile(installLogFile);
		try (PrintStream printStream = new PrintStream(installLogFile, LampAdminConstants.DEFAULT_CHARSET_NAME)) {
			AgentInstallMetadata metadata = createAgentInstallMetadata(targetHost,
																	   hostCredentials,
																	   agentFilePath,
																	   hostConfiguration,
																	   printStream);

			install(result, metadata, printStream);

			result.setMetadata(metadata);

			Map<String, Object> parameters = result.getParameters();

			Optional<Host> hostOptional = hostService.getHostOptional(targetHost.getId());

			if (hostOptional.isPresent()) {
				Host host = hostOptional.get();
				host.setClusterId(targetHost.getClusterId());
				host.setName(targetHost.getName());
				host.setAddress(targetHost.getAddress());
				host.setName(result.getHostname());
				host.setAgentInstallDirectory(metadata.getAgentInstallDirectory());
				host.setAgentInstallFilename(metadata.getAgentInstallFilename());
				host.setAgentFile(metadata.getAgentFile());

				host.setParameters(parameters);
				host.setInstallLogFile(installLogFile.getAbsolutePath());

				hostService.updateHost(host);
			} else {
				Host host = new Host();
				host.setId(targetHost.getId());
				host.setClusterId(targetHost.getClusterId());
				host.setName(targetHost.getName());
				host.setAddress(targetHost.getAddress());
				host.setName(result.getHostname());
				host.setAgentInstallDirectory(metadata.getAgentInstallDirectory());
				host.setAgentInstallFilename(metadata.getAgentInstallFilename());
				host.setAgentFile(metadata.getAgentFile());

				host.setParameters(parameters);
				host.setInstallLogFile(installLogFile.getAbsolutePath());

				hostService.addHost(host);
			}
		} catch (Exception e) {
			result.setException(e);
		}

		return result;
	}

	protected AgentInstallMetadata createAgentInstallMetadata(TargetHost targetHost, HostCredentials hostCredentials, String agentFilePath,
															  HostConfiguration hostConfiguration, PrintStream printStream) throws IOException {
		printStream.println("[1] Generating AgentInstallMetadata");
		AgentInstallMetadata agentInstallMetadata = new AgentInstallMetadata();
		agentInstallMetadata.setAgentId(targetHost.getId());
		agentInstallMetadata.setAddress(targetHost.getAddress());
		agentInstallMetadata.setHostCredentials(hostCredentials);
		agentInstallMetadata.setHostConfiguration(hostConfiguration);
		agentInstallMetadata.setAgentInstallDirectory(agentInstallProperties.getInstallDirectory());
		agentInstallMetadata.setAgentInstallFilename(agentInstallProperties.getInstallFilename());
		agentInstallMetadata.setAgentFile(StringUtils.defaultIfBlank(agentFilePath, agentInstallProperties.getFile()));
		agentInstallMetadata.setAgentInstallScriptCommands(agentInstallProperties.getAgentInstallScriptCommands());

		agentInstallMetadata.setJdkFile(agentInstallProperties.getJdkFilePath());
		agentInstallMetadata.setJdkInstallDirectory(agentInstallProperties.getJdkInstallDirectory());
		agentInstallMetadata.setJdkInstallScriptCommands(null);
		printStream.println("AgentInstallMetadata : " + agentInstallMetadata);
		return agentInstallMetadata;
	}

	public void install(AgentInstallResult result, AgentInstallMetadata agentInstallMetadata, PrintStream printStream) throws IOException {
		log.info("AgentInstall = {}", agentInstallMetadata);
		String address = agentInstallMetadata.getAddress();
		HostCredentials hostCredentials = agentInstallMetadata.getHostCredentials();

		Map<String, Object> parameters = agentInstallProperties.getParameters();
		result.setParameters(parameters);
		parameters.put("agentId", agentInstallMetadata.getAgentId());
		parameters.put("agentPort", agentInstallProperties.getPort());

		try (final SSHClient client = new SSHClient()) {
			printStream.println(String.format("[2] SSH Connect %s:%s", address, hostCredentials.getSshPort()));
			client.addHostKeyVerifier(new PromiscuousVerifier());
			client.connect(address, hostCredentials.getSshPort());

			if (HostAuthType.PASSWORD.equals(hostCredentials.getAuthType())) {
				client.authPassword(hostCredentials.getUsername(), hostCredentials.getPassword());
			} else {
				PKCS8KeyFile keyFile = new PKCS8KeyFile();
				keyFile.init(new StringReader(hostCredentials.getPrivateKey()));
				client.authPublickey(hostCredentials.getUsername(), keyFile);
			}

			// hostname
			try (final Session session = client.startSession()) {
				final Session.Command sessionCommand = session.exec("hostname");
				String response = IOUtils.toString(sessionCommand.getInputStream());
				sessionCommand.join(10, TimeUnit.SECONDS);
				printStream.println(response);
				result.setHostname(StringUtils.trim(response));

				printStream.println(String.format("[3] Get hostname : %s", StringUtils.trim(response)));
			}

			// JDK Install
			if (StringUtils.isNotBlank(agentInstallMetadata.getJdkFile())) {
				log.info("JDK Installing...");
				printStream.println(String.format("[3] JDK Install : %s", agentInstallMetadata.getJdkFile()));
				String localFile = agentInstallMetadata.getJdkFile();
				String remoteInstallDirectory = agentInstallMetadata.getJdkInstallDirectory();
				String remoteInstallFilename = FilenameUtils.getName(localFile);
				String remoteInstallFullPath = Paths.get(remoteInstallDirectory, remoteInstallFilename).toString();
				List<ScriptCommand> scriptCommands = agentInstallMetadata.getJdkInstallScriptCommands();

				install(client, localFile, remoteInstallDirectory, remoteInstallFilename, scriptCommands, parameters, printStream);

				ScriptExecuteCommand unTarCommand = new ScriptExecuteCommand();
				unTarCommand.setCommandLine("tar -xvf " + remoteInstallFullPath + " -C " + remoteInstallDirectory);

				String unTarResponse = executeScriptCommand(client, remoteInstallDirectory, unTarCommand, parameters, printStream);
				String javaHome = null;
				String[] lines = StringUtils.split(unTarResponse, '\n');
				if (ArrayUtils.isNotEmpty(lines)) {
					String firstLine = lines[0];
					javaHome = Paths.get(remoteInstallDirectory, StringUtils.trim(firstLine)).toString();
				}
				log.info("JAVA_HOME = {}", javaHome);
				printStream.println(String.format(" - JAVA_HOME : %s", javaHome));
				parameters.put("JAVA_HOME", javaHome);
			}

			// Agent Install
			{
				log.info("Agent Installing...");
				String localFile = agentInstallMetadata.getAgentFile();
				String remoteInstallDirectory = agentInstallMetadata.getAgentInstallDirectory();
				String remoteInstallFilename = agentInstallMetadata.getAgentInstallFilename();
				List<ScriptCommand> scriptCommands = agentInstallMetadata.getAgentInstallScriptCommands();

				printStream.println(String.format("[4] Agent Install : %s -> %s/%s", localFile, remoteInstallDirectory, remoteInstallFilename));
				install(client, localFile, remoteInstallDirectory, remoteInstallFilename, scriptCommands, parameters, printStream);

				// Start
				try (final Session session = client.startSession()) {
					try (Session.Shell shell = session.startShell()) {
						try (Expect expect = new ExpectBuilder()
							.withOutput(shell.getOutputStream())
							.withInputs(shell.getInputStream(), shell.getErrorStream())
							.withEchoOutput(printStream)
							.withEchoInput(printStream)
							//        .withInputFilters(removeColors(), removeNonPrintable())
							.withExceptionOnFailure()
							.build()) {
							expect.sendLine("cd " + remoteInstallDirectory);
							//						expect.expect(anyOf(contains("#"), contains("$")));
							expect.sendLine("./lamp-agent.sh start");
							//						expect.expect();
						}
					}
				}
			}

//			// Start - Expect 로 변경 https://github.com/Alexey1Gavrilov/expectit
//			try (final Session session = client.startSession()) {
//				final Session.Command sessionCommand = session.exec("cd " + agentInstallDirectory + ";./lamp-agent.sh start");
//				String response = IOUtils.toString(sessionCommand.getInputStream());
//				sessionCommand.join(10, TimeUnit.SECONDS);
//				printStream.println(response);
//				log.error("response = {}", response);
//			}
;
		}
	}

	protected void install(SSHClient client,
						   String localFile, String remoteInstallDirectory,
						   String remoteInstallFilename,
						   List<ScriptCommand> scriptCommands,
						   Map<String, Object> parameters, PrintStream printStream) throws IOException {
		// File Copy
		try (final Session session = client.startSession()) {
			String command = "mkdir -p " + remoteInstallDirectory;
			printStream.println(String.format(" - EXEC : %s", command));
			final Session.Command sessionCommand = session.exec(command);
			String response = IOUtils.toString(sessionCommand.getInputStream());
			sessionCommand.join(10, TimeUnit.SECONDS);
			printStream.println(String.format(" - EXEC-RESULT : %s", command));
		}

		String remoteFilename = Paths.get(remoteInstallDirectory, remoteInstallFilename).toString();
		Resource agentResource = agentInstallProperties.getResource(localFile);
		printStream.println(String.format(" - SCP : %s -> %s", agentResource.getFile().getAbsolutePath(), remoteFilename));
		client.newSCPFileTransfer().upload(new FileSystemFile(agentResource.getFile()), remoteFilename);

		executeScriptCommands(client, remoteInstallDirectory, scriptCommands, parameters, printStream);
	}

	protected void executeScriptCommands(SSHClient client, String workDirectory,
										 List<ScriptCommand> scriptCommandEntities,
										 Map<String, Object> parameters,
										 PrintStream printStream) throws IOException {
		if (CollectionUtils.isEmpty(scriptCommandEntities)) {
			return;
		}

		for (ScriptCommand scriptCommand : scriptCommandEntities) {
			executeScriptCommand(client, workDirectory, scriptCommand, parameters, printStream);
		}
	}

	protected String executeScriptCommand(SSHClient client, String workDirectory,
										ScriptCommand scriptCommand,
										Map<String, Object> parameters,
										PrintStream printStream)
		throws IOException {

		if (scriptCommand instanceof ScriptExecuteCommand) {
			try (final Session session = client.startSession()) {
				String commandLine = expressionParser.getValue(((ScriptExecuteCommand) scriptCommand).getCommandLine(), parameters);
				printStream.println(String.format(" - EXEC : %s", commandLine));
				final Session.Command sessionCommand = session.exec(commandLine);
				String response = IOUtils.toString(sessionCommand.getInputStream());
				sessionCommand.join(10, TimeUnit.SECONDS);
				printStream.println(String.format(" - EXEC-RESULT : %s", response));
				return response;
			}
		} else if (scriptCommand instanceof ScriptFileCreateCommand) {
			ScriptFileCreateCommand fileCreateCommand = (ScriptFileCreateCommand) scriptCommand;
			String filename = fileCreateCommand.getFilename();
			String localFilename = FilenameUtils.getName(filename);
			File localFile = null;
			try {
				localFile = File.createTempFile(FilenameUtils.getBaseName(localFilename), FilenameUtils.getExtension(localFilename));
				printStream.println(String.format(" - CREATE LOCAL FILE : %s", localFile.getAbsolutePath()));
				printStream.println("---BEGIN FILE---");
				log.info("localFile = {}", localFile.getAbsolutePath());
				// FIXME EL 추가
				log.info("file content = {}", fileCreateCommand.getContent());

				try (BufferedReader reader = new BufferedReader(new StringReader(fileCreateCommand.getContent()));
					BufferedWriter writer = new BufferedWriter(new FileWriter(localFile))) {
					String line;
					for (int i = 0; (line = reader.readLine()) != null; i++) {
						if (i > 0) {
							writer.write(LF); // os type?
						}
						String parsedLine = expressionParser.getValue(line, parameters);
						writer.write(parsedLine);
						printStream.println(parsedLine);
					}
					writer.flush();
				}
				printStream.println("---END FILE---");
				String remoteFilename = Paths.get(workDirectory, filename).toString();
				printStream.println(String.format(" - - SCP : %s -> %s", localFile.getAbsolutePath(), remoteFilename));
				client.newSCPFileTransfer().upload(new FileSystemFile(localFile), remoteFilename);

				if (fileCreateCommand.isExecutable()) {
					try (final Session session = client.startSession()) {
						String command = "chmod +x " + remoteFilename;
						printStream.println(String.format(" - EXEC : %s", command));
						final Session.Command sessionCommand = session.exec(command);
						String response = IOUtils.toString(sessionCommand.getInputStream());
						sessionCommand.join(10, TimeUnit.SECONDS);
						printStream.println(String.format(" - EXEC-RESULT : %s", response));
						return response;
					}
				}
				return null;
			} catch (Exception e) {
				log.warn("FileCreateCommand Failed", e);
				throw Exceptions.newException(LampErrorCode.SCRIPT_COMMAND_EXECUTION_FAILED, e);
			} finally {
				FileUtils.deleteQuietly(localFile);
			}

		} else if (scriptCommand instanceof ScriptFileRemoveCommand) {
			// FIXME 구현바람
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, scriptCommand.getClass());
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, scriptCommand.getClass());
		}

	}

}



