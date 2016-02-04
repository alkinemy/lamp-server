package lamp.server.aladin.core.service;

import lamp.server.aladin.admin.security.SecurityUtils;
import lamp.server.aladin.core.domain.AppResource;
import lamp.server.aladin.core.domain.AppTemplate;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.dto.AgentInstallForm;
import lamp.server.aladin.core.exception.EntityNotFoundException;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.support.ExpressionParser;
import lamp.server.aladin.core.support.ssh.SshClient;
import lamp.server.aladin.utils.FileUtils;
import lamp.server.aladin.utils.FilenameUtils;
import lamp.server.aladin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AgentManagementService {

	@Autowired
	private TargetServerService targetServerService;

	@Autowired
	private AppResourceService appResourceService;

	@Autowired
	private AppTemplateService appTemplateService;

	private ExpressionParser expressionParser = new ExpressionParser();

	@Transactional
	public void installAgent(Long targetServerId, AgentInstallForm installForm) {
		Optional<AppTemplate> appTemplateFromDb = appTemplateService.getAppTemplateOptional(installForm.getTemplateId());
		AppTemplate appTemplate = appTemplateFromDb.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, installForm.getTemplateId()));
		String version = installForm.getVersion();

		Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerOptional(targetServerId);
		TargetServer targetServer = targetServerFromDb.orElseThrow(EntityNotFoundException::new);

		AppResource resource = appResourceService.getResource(appTemplate, version);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("groupId", resource.getGroupId());
		parameters.put("artifactId", resource.getArtifactId());
		parameters.put("version", resource.getVersion());

		File file = null;
		String filename = expressionParser.getValue(appTemplate.getAppFilename(), parameters);
		if (StringUtils.isBlank(filename)) {
			filename = resource.getFilename();
			if (StringUtils.isBlank(filename)) {
				filename = "lamp-agent.jar";
			}
		}
		parameters.put("filename", filename);

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
			String username = StringUtils.defaultString(installForm.getUsername(), targetServer.getUsername());
			String password = StringUtils.defaultString(installForm.getPassword(), targetServer.getPassword());

			SshClient sshClient = new SshClient(host, port);
			sshClient.connect(username, password);

			String agentPath = targetServer.getAgentInstallPath();
			if (StringUtils.isBlank(agentPath)) {
				agentPath = appTemplate.getAppDirectory();
				targetServer.setAgentInstallPath(agentPath);
			}
			sshClient.mkdir(agentPath);

			String remoteFilename = Paths.get(agentPath, filename).toString();
			sshClient.scpTo(file, remoteFilename);

			targetServer.setAgentInstalled(true);
			targetServer.setAgentInstalledBy(SecurityUtils.getCurrentUserLogin());
			targetServer.setAgentInstalledDate(LocalDateTime.now());
			targetServer.setAgentInstallFilename(filename);
			targetServer.setAgentStartCommandLine(expressionParser.getValue(appTemplate.getStartCommandLine(), parameters));
		} catch (Exception e) {
			log.warn("Agent Install failed", e);
			throw Exceptions.newException(LampErrorCode.AGENT_INSTALL_FAILED, e);
		} finally {
			if (isTempFile && file != null) {
				file.delete();
			}
		}
	}

	public void startAgent(Long targetServerId, String inputUsername, String inputPassword, PrintStream printStream) {
		Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerOptional(targetServerId);
		TargetServer targetServer = targetServerFromDb.orElseThrow(EntityNotFoundException::new);

		String agentPath = targetServer.getAgentInstallPath();
		String filename = targetServer.getAgentInstallFilename();

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("filename", filename);

		String startCommand = targetServer.getAgentStartCommandLine();
		startCommand = expressionParser.getValue(startCommand, parameters);
		log.debug("[TargetServer:{}] Agent startCommandLine = {}", targetServerId, startCommand);

		String host = targetServer.getAddress();
		int port = targetServer.getSshPort();
		String username = StringUtils.defaultString(inputUsername, targetServer.getUsername());
		String password = StringUtils.defaultString(inputPassword, targetServer.getPassword());

		SshClient sshClient = new SshClient(host, port);
		sshClient.connect(username, password);

		long timeout = 5 * 1000;
		sshClient.exec(agentPath, startCommand, printStream, timeout);
	}

}
