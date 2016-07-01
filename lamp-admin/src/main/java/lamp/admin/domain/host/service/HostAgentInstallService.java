package lamp.admin.domain.host.service;

import lamp.admin.core.host.HostCredentials;
import lamp.admin.core.script.ScriptCommand;
import lamp.admin.core.script.ScriptExecuteCommand;
import lamp.admin.core.script.ScriptFileCreateCommand;
import lamp.admin.core.script.ScriptFileRemoveCommand;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.host.model.*;
import lamp.admin.domain.host.model.entity.HostEntity;

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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static net.sf.expectit.matcher.Matchers.regexp;

@Slf4j
@Service
public class HostAgentInstallService {

	private static final char LF = '\n';
	private static final char CR = '\r';

	private ExpressionParser expressionParser = new ExpressionParser();

	@Autowired
	private AgentInstallProperties agentInstallProperties;

	@Autowired
	private HostEntityService hostEntityService;

	@Transactional
	public AgentInstallResult installAgent(TargetHost targetHost,
										   HostCredentials hostCredentials,
										   String agentFilePath,
										   HostConfiguration hostConfiguration) {
		PrintStream printStream = System.out;

		String agentId = UUID.randomUUID().toString();
		AgentInstallResult result;

		HostEntity hostEntity = new HostEntity();
		hostEntity.setId(agentId);
		hostEntity.setClusterId(targetHost.getClusterId());
		hostEntity.setName(targetHost.getAddress());
		hostEntity.setAddress(targetHost.getAddress());


		try {
			AgentInstall agentInstall = new AgentInstall();
			agentInstall.setAgentId(agentId);
			agentInstall.setAddress(targetHost.getHostname());
			agentInstall.setHostCredentials(hostCredentials);
			agentInstall.setHostConfiguration(hostConfiguration);
			agentInstall.setAgentInstallDirectory(agentInstallProperties.getInstallDirectory());
			agentInstall.setAgentInstallFilename(agentInstallProperties.getInstallFilename());
			agentInstall.setAgentFile(StringUtils.defaultIfBlank(agentFilePath, agentInstallProperties.getFile()));
			agentInstall.setAgentInstallScriptCommands(agentInstallProperties.getAgentInstallScriptCommands());

			agentInstall.setJdkFile(agentInstallProperties.getJdkFilePath());
			agentInstall.setJdkInstallDirectory(agentInstallProperties.getJdkInstallDirectory());
			agentInstall.setJdkInstallScriptCommands(null);

			result = install(agentInstall, printStream);

			hostEntity.setName(result.getHostname());
			hostEntity.setAgentInstallDirectory(agentInstall.getAgentInstallDirectory());
			hostEntity.setAgentInstallFilename(agentInstall.getAgentInstallFilename());
			hostEntity.setAgentFile(agentInstall.getAgentFile());
		} catch (Exception e) {
			log.error("Agent install failed", e);
			result = new AgentInstallResult();
			result.setError(ExceptionUtils.getStackTrace(e));
		}

		hostEntityService.create(hostEntity);

		return result;
	}

	public AgentInstallResult install(AgentInstall agentInstall, PrintStream printStream) {
		log.info("AgentInstall = {}", agentInstall);
		String address = agentInstall.getAddress();
		HostCredentials hostCredentials = agentInstall.getHostCredentials();
		AgentInstallResult result = new AgentInstallResult();
		result.setAddress(address);

		Map<String, Object> parameters = agentInstallProperties.getParameters();
		parameters.put("agentId", agentInstall.getAgentId());
		parameters.put("agentPort", agentInstallProperties.getPort());

		try (final SSHClient client = new SSHClient()) {
			client.addHostKeyVerifier(new PromiscuousVerifier());
			client.connect(address, hostCredentials.getSshPort());

			if (hostCredentials.isUsePassword()) {
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
			}

			// JDK Install
			if (StringUtils.isNotBlank(agentInstall.getJdkFile())) {
				log.info("JDK Installing...");
				String localFile = agentInstall.getJdkFile();
				String remoteInstallDirectory = agentInstall.getJdkInstallDirectory();
				String remoteInstallFilename = FilenameUtils.getName(localFile);
				String remoteInstallFullPath = Paths.get(remoteInstallDirectory, remoteInstallFilename).toString();
				List<ScriptCommand> scriptCommands = agentInstall.getJdkInstallScriptCommands();

				install(agentInstall, printStream, client, localFile, remoteInstallDirectory, remoteInstallFilename, scriptCommands, parameters);

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
				parameters.put("JAVA_HOME", javaHome);
			}

			// Agent Install
			{
				log.info("Agent Installing...");
				String localFile = agentInstall.getAgentFile();
				String remoteInstallDirectory = agentInstall.getAgentInstallDirectory();
				String remoteInstallFilename = agentInstall.getAgentInstallFilename();
				List<ScriptCommand> scriptCommands = agentInstall.getAgentInstallScriptCommands();

				install(agentInstall, printStream, client, localFile, remoteInstallDirectory, remoteInstallFilename, scriptCommands, parameters);

				// Start
				try (final Session session = client.startSession()) {
					try (Session.Shell shell = session.startShell()) {
						try (Expect expect = new ExpectBuilder()
							.withOutput(shell.getOutputStream())
							.withInputs(shell.getInputStream(), shell.getErrorStream())
							.withEchoOutput(System.out)
							.withEchoInput(System.err)
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
		} catch (Exception e) {
			log.error("Agent install failed", e);
			result.setError(ExceptionUtils.getStackTrace(e));
		}
		return result;
	}

	protected void install(AgentInstall agentInstall, PrintStream printStream,
						   SSHClient client,
						   String localFile, String remoteInstallDirectory,
						   String remoteInstallFilename,
						   List<ScriptCommand> scriptCommands,
						   Map<String, Object> parameters) throws IOException {
		// File Copy
		try (final Session session = client.startSession()) {
			final Session.Command sessionCommand = session.exec("mkdir -p " + remoteInstallDirectory);
			String response = IOUtils.toString(sessionCommand.getInputStream());
			sessionCommand.join(10, TimeUnit.SECONDS);
			printStream.println(response);
		}

		String remoteFilename = Paths.get(remoteInstallDirectory, remoteInstallFilename).toString();
		Resource agentResource = agentInstallProperties.getResource(localFile);
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
				final Session.Command sessionCommand = session.exec(commandLine);
				String response = IOUtils.toString(sessionCommand.getInputStream());
				sessionCommand.join(10, TimeUnit.SECONDS);
				printStream.println(response);
				return response;
			}
		} else if (scriptCommand instanceof ScriptFileCreateCommand) {
			ScriptFileCreateCommand fileCreateCommand = (ScriptFileCreateCommand) scriptCommand;
			String filename = fileCreateCommand.getFilename();
			String localFilename = FilenameUtils.getName(filename);
			File localFile = null;
			try {
				localFile = File.createTempFile(FilenameUtils.getBaseName(localFilename), FilenameUtils.getExtension(localFilename));
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
					}
					writer.flush();
				}
				String remoteFilename = Paths.get(workDirectory, filename).toString();
				client.newSCPFileTransfer().upload(new FileSystemFile(localFile), remoteFilename);

				if (fileCreateCommand.isExecutable()) {
					try (final Session session = client.startSession()) {
						final Session.Command sessionCommand = session.exec("chmod +x " + remoteFilename);
						String response = IOUtils.toString(sessionCommand.getInputStream());
						sessionCommand.join(10, TimeUnit.SECONDS);
						printStream.println(response);
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



