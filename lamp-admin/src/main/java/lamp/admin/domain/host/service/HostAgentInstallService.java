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

		AgentInstall agentInstall = new AgentInstall();
		agentInstall.setAgentId(UUID.randomUUID().toString());
		agentInstall.setAddress(targetHost.getHostname());
		agentInstall.setHostCredentials(hostCredentials);
		agentInstall.setHostConfiguration(hostConfiguration);
		agentInstall.setAgentInstallDirectory(agentInstallProperties.getInstallDirectory());
		agentInstall.setAgentInstallFilename(agentInstallProperties.getInstallFilename());
		agentInstall.setAgentFile(StringUtils.defaultIfBlank(agentFilePath, agentInstallProperties.getFile()));

		AgentInstallResult result = installAgent(agentInstall, printStream);

		HostEntity hostEntity = new HostEntity();
		hostEntity.setId(agentInstall.getAgentId());
		hostEntity.setClusterId(targetHost.getClusterId());
		hostEntity.setName(result.getHostname());
		hostEntity.setAddress(targetHost.getAddress());
		hostEntity.setAgentInstallDirectory(agentInstall.getAgentInstallDirectory());
		hostEntity.setAgentInstallFilename(agentInstall.getAgentInstallFilename());
		hostEntity.setAgentFile(agentInstall.getAgentFile());


		hostEntityService.create(hostEntity);

		return result;
	}

	public AgentInstallResult installAgent(AgentInstall agentInstall, PrintStream printStream) {

		String address = agentInstall.getAddress();
		HostCredentials hostCredentials = agentInstall.getHostCredentials();
		String agentFile = agentInstall.getAgentFile();
		String agentInstallDirectory = agentInstall.getAgentInstallDirectory();
		String agentInstallFilename = agentInstall.getAgentInstallFilename();

		AgentInstallResult result = new AgentInstallResult();
		result.setAddress(address);

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
			// File Copy
			try (final Session session = client.startSession()) {
				final Session.Command sessionCommand = session.exec("mkdir -p " + agentInstallDirectory);
				String response = IOUtils.toString(sessionCommand.getInputStream());
				sessionCommand.join(10, TimeUnit.SECONDS);
				printStream.println(response);
			}

			String remoteFilename = Paths.get(agentInstallDirectory, agentInstallFilename).toString();
			Resource agentResource = agentInstallProperties.getResource(agentFile);
			client.newSCPFileTransfer().upload(new FileSystemFile(agentResource.getFile()), remoteFilename);

			// ScriptCommands
			Map<String, Object> parameters = agentInstallProperties.getParameters();
			parameters.put("agentId", agentInstall.getAgentId());
			parameters.put("agentPort", agentInstallProperties.getPort());



			List<ScriptCommand> scriptCommandEntities = agentInstallProperties.getInstallScriptCommands();
			executeScriptCommands(client, agentInstallDirectory, scriptCommandEntities, parameters, printStream);


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
						expect.sendLine("cd " + agentInstallDirectory);
//						expect.expect(anyOf(contains("#"), contains("$")));
						expect.sendLine("./lamp-agent.sh start");
//						expect.expect();
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

	protected void executeScriptCommand(SSHClient client, String workDirectory,
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
					}
				}
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



