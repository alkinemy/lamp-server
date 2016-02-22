package lamp.admin.core.agent.service;

import lamp.admin.core.agent.domain.*;
import lamp.admin.core.app.domain.AppInstallScript;
import lamp.admin.core.app.service.AppInstallScriptService;
import lamp.admin.core.app.service.AppResourceService;
import lamp.admin.core.app.service.AppTemplateService;
import lamp.admin.core.app.domain.AppResource;
import lamp.admin.core.app.domain.AppTemplate;
import lamp.admin.core.base.exception.EntityNotFoundException;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.core.script.domain.ScriptCommand;
import lamp.admin.core.script.domain.ExecuteCommand;
import lamp.admin.core.script.domain.FileCreateCommand;
import lamp.admin.core.script.domain.FileRemoveCommand;
import lamp.admin.core.support.el.ExpressionParser;
import lamp.admin.core.support.ssh.SshClient;
import lamp.admin.utils.FileUtils;
import lamp.admin.utils.FilenameUtils;
import lamp.admin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AgentManagementService {

	@Autowired
	private TargetServerService targetServerService;

	@Autowired
	private AppResourceService appResourceService;

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AppInstallScriptService appInstallScriptService;

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

			SshClient sshClient = new SshClient(host, port);
			if (SshAuthType.KEY.equals(targetServer.getAuthType())) {
				sshClient.connect(username, targetServer.getPrivateKey(), password);
			} else {
				sshClient.connect(username, password);
			}

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
			targetServer.setAgentPidFile(parameters.get("pidFile"));
			targetServer.setAgentStartCommandLine(parameters.get("startCommandLine"));
			targetServer.setAgentStopCommandLine(parameters.get("stopCommandLine"));

			if (installForm.getInstallScriptId() != null) {
				executeInstallScript(targetServer, sshClient, installForm.getInstallScriptId(), printStream);
			}


		} catch (Exception e) {
			log.warn("Agent Install failed", e);
			throw Exceptions.newException(LampErrorCode.AGENT_INSTALL_FAILED, e);
		} finally {
			if (isTempFile && file != null) {
				file.delete();
			}
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

		return tempParameters.entrySet().stream().collect(Collectors.toMap(
			e -> e.getKey(),
			e -> expressionParser.getValue(e.getValue() != null ? String.valueOf(e.getValue()) : null, tempParameters)
		));
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
		if (command instanceof ExecuteCommand) {
			long timeout = 10 * 1000;
			String commandLine = expressionParser.getValue(((ExecuteCommand) command).getCommandLine(), parameters);
			sshClient.exec(agentPath, commandLine, printStream, timeout);
		} else if (command instanceof FileCreateCommand) {
			String filename = ((FileCreateCommand) command).getFilename();
			File file = new File(FilenameUtils.getName(filename));
			String remoteFilename = Paths.get(agentPath, filename).toString();
			sshClient.scpTo(file, remoteFilename, true);
		} else if (command instanceof FileRemoveCommand) {
			// FIXME 구현바람
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, command.getType());
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, command.getType());
		}
	}

	public void startAgent(Long targetServerId, AgentStartForm startForm, PrintStream printStream) {
		TargetServer targetServer = targetServerService.getTargetServer(targetServerId);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("filename", targetServer.getAgentInstallFilename());

		String command = startForm.getCommandLine();
		command = expressionParser.getValue(command, parameters);
		log.debug("[TargetServer:{}] Agent startCommandLine = {}", targetServerId, command);

		executeCmd(targetServer, startForm.getPassword(), command, printStream);
	}

	public void stopAgent(Long targetServerId, AgentStopForm stopForm, PrintStream printStream) {
		TargetServer targetServer = targetServerService.getTargetServer(targetServerId);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("filename", targetServer.getAgentInstallFilename());
		parameters.put("pidFile", targetServer.getAgentPidFile());

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

		SshClient sshClient = new SshClient(host, port);
		if (SshAuthType.KEY.equals(targetServer.getAuthType())) {
			sshClient.connect(username, targetServer.getPrivateKey(), password);
		} else {
			sshClient.connect(username, password);
		}

		long timeout = 5 * 1000;
		String agentPath = targetServer.getAgentInstallPath();
		sshClient.exec(agentPath, command, printStream, timeout);
	}
}
