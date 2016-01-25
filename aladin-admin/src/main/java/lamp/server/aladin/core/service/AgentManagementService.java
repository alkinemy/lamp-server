package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AppTemplate;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.dto.AgentInstallForm;
import lamp.server.aladin.core.dto.TargetServerCreateForm;
import lamp.server.aladin.core.dto.TargetServerDto;
import lamp.server.aladin.core.exception.EntityNotFoundException;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.repository.TargetServerRepository;
import lamp.server.aladin.core.support.ssh.SshClient;
import lamp.server.aladin.utils.FileUtils;
import lamp.server.aladin.utils.FilenameUtils;
import lamp.server.aladin.utils.StringUtils;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
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

	public void installAgent(Long targetServerId, AgentInstallForm installForm) {
		Optional<AppTemplate> appTemplateFromDb = appTemplateService.getAppTemplate(installForm.getTemplateId());
		AppTemplate appTemplate = appTemplateFromDb.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, installForm.getTemplateId()));
		String version = installForm.getVersion();

		Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServer(targetServerId);
		TargetServer targetServer = targetServerFromDb.orElseThrow(EntityNotFoundException::new);

		Resource resource = appResourceService.getResource(appTemplate, version);
		File file = null;
		String filename;
		boolean isTempFile = false;
		try {

			try {
				file = resource.getFile();
				filename = file.getName();
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
			sshClient.mkdir(agentPath);

			String remoteFilename = Paths.get(agentPath, filename).toString();
			sshClient.scpTo(file, remoteFilename);

			String startCommand = appTemplate.getStartCommandLine();
			if (StringUtils.isBlank(startCommand)) {
				startCommand = "nohup java -jar " + file.getName() + " --server.port=8080 1>agent.out 2>&1 &";
			}
			// TODO 수정 바람
			startCommand = "nohup java -jar " + file.getName() + " --server.port=18080 1>agent.out 2>&1 &";

			try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
					PrintStream printStream = new PrintStream(baos)) {
				sshClient.exec(agentPath, startCommand, printStream, 10 * 1000);
				String output = baos.toString("UTF-8");
				log.info("exec : {}", output);
			}

		} catch (Exception e) {
			log.error("에이전트 설치 실패", e);
			throw Exceptions.newException(LampErrorCode.AGENT_INSTALL_FAILED, e);
		} finally {
			if (isTempFile && file != null) {
				file.delete();
			}
		}


	}

}
