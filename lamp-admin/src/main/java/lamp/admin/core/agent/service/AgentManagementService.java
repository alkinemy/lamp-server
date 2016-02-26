package lamp.admin.core.agent.service;

import lamp.admin.core.agent.domain.*;
import lamp.admin.core.app.domain.AppInstallScript;
import lamp.admin.core.app.domain.AppResource;
import lamp.admin.core.app.domain.AppTemplate;
import lamp.admin.core.app.domain.ManagedApp;
import lamp.admin.core.app.service.AppInstallScriptService;
import lamp.admin.core.app.service.AppResourceService;
import lamp.admin.core.app.service.AppTemplateService;
import lamp.admin.core.app.service.ManagedAppService;
import lamp.admin.core.base.exception.EntityNotFoundException;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.core.base.exception.MessageException;
import lamp.admin.core.script.domain.ExecuteCommand;
import lamp.admin.core.script.domain.FileCreateCommand;
import lamp.admin.core.script.domain.FileRemoveCommand;
import lamp.admin.core.script.domain.ScriptCommand;
import lamp.admin.core.support.el.ExpressionParser;
import lamp.admin.core.support.ssh.SshClient;
import lamp.admin.utils.FileUtils;
import lamp.admin.utils.FilenameUtils;
import lamp.admin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AgentManagementService {

	private static final char LF = '\n';
	private static final char CR = '\r';

	@Autowired
	private TargetServerService targetServerService;

	@Autowired
	private AppResourceService appResourceService;

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AppInstallScriptService appInstallScriptService;

	@Autowired
	private SshKeyService sshKeyService;

	@Autowired
	private ManagedAppService managedAppService;

	private ExpressionParser expressionParser = new ExpressionParser();

	@Transactional
	public void installAgent(Long targetServerId, AgentInstallForm installForm, String agentInstalledBy, PrintStream printStream) {
		AppTemplate appTemplate = appTemplateService.getAppTemplate(installForm.getTemplateId());
		String version = installForm.getVersion();

		Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerOptional(targetServerId);
		TargetServer targetServer = targetServerFromDb.orElseThrow(EntityNotFoundException::new);

		AppResource resource = appResourceService.getResource(appTemplate, version);

		Map<String, String> parameters = getParameters(appTemplate, resource);

		File file = null;
		String filename = parameters.get("filename");
		if (StringUtils.isBlank(filename)) {
			filename = resource.getFilename();
			if (StringUtils.isBlank(filename)) {
				filename = "lamp-agent.jar";
				parameters.put("filename", filename);
			}
		}


		boolean isTempFile = false;
		try {
			try {
				file = resource.getFile();
			} catch (IOException ie) {
				file = File.createTempFile(FilenameUtils.getBaseName(filename), "." + FilenameUtils.getExtension(filename));
				isTempFile = true;
				FileUtils.copyInputStreamToFile(resource.getInputStream(), file);
			}
			// SSH
			String host = targetServer.getAddress();
			int port = targetServer.getSshPort();
			String username = targetServer.getUsername();
			String password = StringUtils.defaultString(installForm.getPassword(), targetServer.getPassword());

			SshClient sshClient = getSshClient(targetServer, host, port, username, password);

			String agentPath = targetServer.getAgentInstallPath();
			if (StringUtils.isBlank(agentPath)) {
				agentPath = appTemplate.getAppDirectory();
				targetServer.setAgentInstallPath(agentPath);
			}
			String remoteFilename = Paths.get(agentPath, filename).toString();
			sshClient.scpTo(file, remoteFilename, true);

			targetServer.setAgentInstalled(true);
			targetServer.setAgentInstalledBy(agentInstalledBy);
			targetServer.setAgentInstalledDate(LocalDateTime.now());
			targetServer.setAgentInstallFilename(filename);
			targetServer.setAgentGroupId(resource.getGroupId());
			targetServer.setAgentArtifactId(resource.getArtifactId());
			targetServer.setAgentVersion(resource.getVersion());
			if (StringUtils.isBlank(targetServer.getAgentPidFile())) {
				targetServer.setAgentPidFile(appTemplate.getPidFile());
			}
			if (StringUtils.isBlank(targetServer.getAgentStartCommandLine())) {
				targetServer.setAgentStartCommandLine(appTemplate.getStartCommandLine());
			}
			if (StringUtils.isBlank(targetServer.getAgentStopCommandLine())) {
				targetServer.setAgentStopCommandLine(appTemplate.getStopCommandLine());

			}

			if (installForm.getInstallScriptId() != null) {
				executeInstallScript(targetServer, sshClient, installForm.getInstallScriptId(), printStream);
			}

		} catch (MessageException e) {
			log.warn("Agent Install failed", e);
			throw e;
		} catch (Exception e) {
			log.warn("Agent Install failed", e);
			throw Exceptions.newException(LampErrorCode.AGENT_INSTALL_FAILED, e);
		} finally {
			if (isTempFile && file != null) {
				file.delete();
			}
		}
	}

	protected void executeInstallScript(TargetServer targetServer, SshClient sshClient, Long installScriptId, PrintStream printStream) {
		AppInstallScript installScript = appInstallScriptService.getAppInstallScript(installScriptId);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("filename", targetServer.getAgentInstallFilename());

		List<ScriptCommand> scriptCommands = installScript.getCommands();
		scriptCommands.stream().forEach(sc -> executeScriptCommand(targetServer, sshClient, sc, parameters, printStream));
	}



	protected void executeScriptCommand(TargetServer targetServer, SshClient sshClient, ScriptCommand command, Map<String, Object> parameters, PrintStream printStream) {
		String agentPath = targetServer.getAgentInstallPath();
		long timeout = 10 * 1000;
		if (command instanceof ExecuteCommand) {
			String commandLine = expressionParser.getValue(((ExecuteCommand) command).getCommandLine(), parameters);
			sshClient.exec(agentPath, commandLine, printStream, timeout);
		} else if (command instanceof FileCreateCommand) {
			FileCreateCommand fileCreateCommand = (FileCreateCommand) command;
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
						writer.write(line);
					}
					writer.flush();
				}
				String remoteFilename = Paths.get(agentPath, filename).toString();
				log.info("file2 content = {}", FileUtils.readFileToString(localFile));
				sshClient.scpTo(localFile, remoteFilename, true);
				if (fileCreateCommand.isExecutable()) {
					sshClient.exec(agentPath, "chmod +x " + FilenameUtils.getName(remoteFilename), printStream, timeout);
				}
			} catch (Exception e) {
				log.warn("FileCreateCommand Failed", e);
				throw Exceptions.newException(LampErrorCode.SCRIPT_COMMAND_EXECUTION_FAILED, e);
			} finally {
				FileUtils.deleteQuietly(localFile);
			}

		} else if (command instanceof FileRemoveCommand) {
			// FIXME 구현바람
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, command.getType());
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, command.getType());
		}
	}


	protected Map<String, String> getParameters(AppTemplate appTemplate, AppResource resource) {
		Map<String, Object> tempParameters = new HashMap<>();
		tempParameters.put("groupId", resource.getGroupId());
		tempParameters.put("artifactId", resource.getArtifactId());
		tempParameters.put("version", resource.getVersion());
		tempParameters.put("appDirectory", appTemplate.getAppDirectory());
		tempParameters.put("workDirectory", appTemplate.getWorkDirectory());
		tempParameters.put("logDirectory", appTemplate.getLogDirectory());
		tempParameters.put("pidFile", appTemplate.getPidFile());
		tempParameters.put("stdOutFile", appTemplate.getStdOutFile());
		tempParameters.put("stdErrFile", appTemplate.getStdErrFile());
		tempParameters.put("filename", appTemplate.getAppFilename());
		tempParameters.put("startCommandLine", appTemplate.getStartCommandLine());
		tempParameters.put("stopCommandLine", appTemplate.getStopCommandLine());

		return tempParameters.entrySet().stream().filter(e -> e.getValue() != null).collect(Collectors.toMap(
				e -> e.getKey(),
				e -> expressionParser.getValue(String.valueOf(e.getValue()), tempParameters)
		));
	}

	protected Map<String, Object> getParameters(TargetServer targetServer) {
		Map<String, Object> tempParameters = new HashMap<>();
		tempParameters.put("groupId", targetServer.getAgentGroupId());
		tempParameters.put("artifactId", targetServer.getAgentArtifactId());
		tempParameters.put("version", targetServer.getAgentVersion());
		tempParameters.put("appDirectory", targetServer.getAgentInstallPath());
		tempParameters.put("workDirectory", targetServer.getAgentInstallPath());
//		tempParameters.put("logDirectory", appTemplate.getLogDirectory());
		tempParameters.put("pidFile", targetServer.getAgentPidFile());
//		tempParameters.put("stdOutFile", appTemplate.getStdOutFile());
//		tempParameters.put("stdErrFile", appTemplate.getStdErrFile());
		tempParameters.put("filename", targetServer.getAgentInstallFilename());
		tempParameters.put("startCommandLine", targetServer.getAgentStartCommandLine());
		tempParameters.put("stopCommandLine", targetServer.getAgentStopCommandLine());

		return tempParameters.entrySet().stream().filter(e -> e.getValue() != null).collect(Collectors.toMap(
				e -> e.getKey(),
				e -> expressionParser.getValue(String.valueOf(e.getValue()), tempParameters)
		));
	}

	public void startAgent(Long targetServerId, AgentStartForm startForm, PrintStream printStream) {
		TargetServer targetServer = targetServerService.getTargetServer(targetServerId);
		Map<String, Object> parameters = getParameters(targetServer);

		String command = startForm.getCommandLine();
		command = expressionParser.getValue(command, parameters);
		log.debug("[TargetServer:{}] Agent startCommandLine = {}", targetServerId, command);

		executeCmd(targetServer, startForm.getPassword(), command, printStream);
	}

	public void stopAgent(Long targetServerId, AgentStopForm stopForm, PrintStream printStream) {
		TargetServer targetServer = targetServerService.getTargetServer(targetServerId);
		Map<String, Object> parameters = getParameters(targetServer);

		String command = stopForm.getCommandLine();
		command = expressionParser.getValue(command, parameters);
		log.debug("[TargetServer:{}] Agent stopCommandLine = {}", targetServerId, command);

		executeCmd(targetServer, stopForm.getPassword(), command, printStream);
	}

	protected void executeCmd(TargetServer targetServer, String inputPassword, String command, PrintStream printStream) {
		String host = targetServer.getAddress();
		int port = targetServer.getSshPort();
		String username = targetServer.getUsername();
		String password = StringUtils.defaultString(inputPassword, targetServer.getPassword());

		SshClient sshClient = getSshClient(targetServer, host, port, username, password);

		long timeout = 5 * 1000;
		String agentPath = targetServer.getAgentInstallPath();
		sshClient.exec(agentPath, command, printStream, timeout);
	}

	private SshClient getSshClient(TargetServer targetServer, String host, int port, String username, String password) {
		SshClient sshClient = new SshClient(host, port);
		if (SshAuthType.KEY.equals(targetServer.getAuthType())) {
			String privateKey;
			Long sshKeyId = targetServer.getSshKeyId();
			if (sshKeyId != null) {
				SshKey sshKey = sshKeyService.getSshKey(sshKeyId);
				username = sshKey.getUsername();
				password = sshKey.getPassword();
				privateKey = sshKey.getPassword();
			} else {
				privateKey = targetServer.getPrivateKey();
			}
			sshClient.connect(username, privateKey, password);
		} else {
			sshClient.connect(username, password);
		}
		return sshClient;
	}
}
