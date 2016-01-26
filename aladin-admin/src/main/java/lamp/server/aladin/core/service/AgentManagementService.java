package lamp.server.aladin.core.service;

import lamp.server.aladin.admin.security.SecurityUtils;
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

	private ExpressionParser expressionParser;

	@Transactional
	public void installAgent(Long targetServerId, AgentInstallForm installForm) {
		Optional<AppTemplate> appTemplateFromDb = appTemplateService.getAppTemplate(installForm.getTemplateId());
		AppTemplate appTemplate = appTemplateFromDb.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, installForm.getTemplateId()));
		String version = installForm.getVersion();

		Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerOptional(targetServerId);
		TargetServer targetServer = targetServerFromDb.orElseThrow(EntityNotFoundException::new);

		Resource resource = appResourceService.getResource(appTemplate, version);
		File file = null;
		String filename;
		boolean isTempFile = false;
		try {

			try {
				file = resource.getFile();
				filename = resource.getFilename();
			} catch (IOException ie) {
				filename = resource.getFilename();
				if (StringUtils.isBlank(filename)) {
					filename = "lamp-agent.jar";
				}
				file = File.createTempFile(FilenameUtils.getBaseName(filename), "." + FilenameUtils.getExtension(filename));
				isTempFile = true;
				FileUtils.copyInputStreamToFile(resource.getInputStream(), file);
			}

			// passwordless SSH
			String host = targetServer.getAddress();
			int port = targetServer.getSshPort();

			SshClient sshClient = new SshClient(host, port);
			sshClient.connect(targetServer.getUsername(), targetServer.getPassword());

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
		} catch (Exception e) {
			log.warn("Agent Install failed", e);
			throw Exceptions.newException(LampErrorCode.AGENT_INSTALL_FAILED, e);
		} finally {
			if (isTempFile && file != null) {
				file.delete();
			}
		}
	}

	public void startAgent(Long targetServerId, PrintStream printStream) {
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

		SshClient sshClient = new SshClient(host, port);
		sshClient.connect(targetServer.getUsername(), targetServer.getPassword());

		long timeout = 5 * 1000;
		sshClient.exec(agentPath, startCommand, printStream, timeout);
	}

}
