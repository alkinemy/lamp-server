package lamp.admin.domain.host.service;

import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.host.model.*;
import lamp.admin.domain.host.model.entity.HostEntity;
import lamp.admin.domain.script.model.ExecuteCommand;
import lamp.admin.domain.script.model.FileCreateCommand;
import lamp.admin.domain.script.model.FileRemoveCommand;
import lamp.admin.domain.script.model.ScriptCommand;
import lamp.admin.domain.support.el.ExpressionParser;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;
import net.schmizz.sshj.xfer.FileSystemFile;
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
	public void installAgent(TargetHost targetHost,
							 HostCredentials hostCredentials,
							 String agentFilePath,
							 HostConfiguration hostConfiguration) {
		PrintStream printStream = System.out;

		HostEntity hostEntity = new HostEntity();
		hostEntity.setId(UUID.randomUUID().toString());
		hostEntity.setClusterId(targetHost.getClusterId());
		hostEntity.setHostname(targetHost.getHostname());
		hostEntity.setAddress(targetHost.getAddress());

		hostEntity.setAgentInstallDirectory(agentInstallProperties.getAgentInstallDirectory());
		hostEntity.setAgentInstallFilename(agentInstallProperties.getAgentInstallFilename());
		hostEntity.setAgentFile(StringUtils.defaultIfBlank(agentFilePath, agentInstallProperties.getAgentFile()));

		installAgent(hostEntity.getAddress(),
					 hostCredentials,
					 hostEntity.getAgentFile(),
					 hostEntity.getAgentInstallDirectory(),
					 hostEntity.getAgentInstallFilename(),
					 hostConfiguration,
					 printStream);

		hostEntityService.addHostEntity(hostEntity);
	}

	public void installAgent(String address,
							 HostCredentials hostCredentials,
							 String agentFile,
							 String agentInstallDirectory,
							 String agentInstallFilename,
							 HostConfiguration hostConfiguration,
							 PrintStream printStream) {

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
			List<ScriptCommand> scriptCommands = agentInstallProperties.getInstallScriptCommands();
			executeScriptCommands(client, agentInstallDirectory, scriptCommands, parameters, printStream);


			// Start - Expect 로 변경 https://github.com/Alexey1Gavrilov/expectit
			try (final Session session = client.startSession()) {
				final Session.Command sessionCommand = session.exec("cd " + agentInstallDirectory + ";./lamp-agent.sh start");
				String response = IOUtils.toString(sessionCommand.getInputStream());
				sessionCommand.join(10, TimeUnit.SECONDS);
				printStream.println(response);
				log.error("response = {}", response);
			}
		} catch (Exception e) {
			log.error("Agent install failed", e);
			throw Exceptions.newException(AdminErrorCode.AGENT_INSTALL_FAILED, e);
		}
	}

	protected void executeScriptCommands(SSHClient client, String workDirectory,
										 List<ScriptCommand> scriptCommands,
										 Map<String, Object> parameters,
										 PrintStream printStream) throws IOException {
		if (CollectionUtils.isEmpty(scriptCommands)) {
			return;
		}

		for (ScriptCommand scriptCommand : scriptCommands) {
			executeScriptCommand(client, workDirectory, scriptCommand, parameters, printStream);
		}
	}

	protected void executeScriptCommand(SSHClient client, String workDirectory,
										ScriptCommand scriptCommand,
										Map<String, Object> parameters,
										PrintStream printStream)
		throws IOException {

		if (scriptCommand instanceof ExecuteCommand) {
			try (final Session session = client.startSession()) {
				String commandLine = expressionParser.getValue(((ExecuteCommand) scriptCommand).getCommandLine(), parameters);
				final Session.Command sessionCommand = session.exec(commandLine);
				String response = IOUtils.toString(sessionCommand.getInputStream());
				sessionCommand.join(10, TimeUnit.SECONDS);
				printStream.println(response);
			}
		} else if (scriptCommand instanceof FileCreateCommand) {
			FileCreateCommand fileCreateCommand = (FileCreateCommand) scriptCommand;
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

		} else if (scriptCommand instanceof FileRemoveCommand) {
			// FIXME 구현바람
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, scriptCommand.getType());
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, scriptCommand.getType());
		}

	}

}



