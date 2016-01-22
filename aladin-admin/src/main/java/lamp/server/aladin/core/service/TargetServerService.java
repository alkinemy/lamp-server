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
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
public class TargetServerService {

	@Autowired
	private TargetServerRepository targetServerRepository;

	@Autowired
	private AppResourceService appResourceService;

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private SmartAssembler smartAssembler;

	public Optional<TargetServer> getTargetServer(Long id) {
		TargetServer targetServer = targetServerRepository.findOne(id);
		return Optional.ofNullable(targetServer);
	}

	public Optional<TargetServer> getTargetServerByHostname(String hostname) {
		return targetServerRepository.findOneByHostname(hostname);
	}

	@Transactional
	public TargetServer insertTargetServer(TargetServerCreateForm editForm) {
		TargetServer targetServer = smartAssembler.assemble(editForm, TargetServer.class);
		return targetServerRepository.save(targetServer);
	}

	@Transactional
	public TargetServer insertTargetServer(TargetServer targetServer) {
		return targetServerRepository.save(targetServer);
	}

	public void installAgent(Long targetServerId, AgentInstallForm installForm) {
		Optional<AppTemplate> appTemplateFromDb = appTemplateService.getAppTemplate(installForm.getTemplateId());
		AppTemplate appTemplate = appTemplateFromDb.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, installForm.getTemplateId()));
		String version = installForm.getVersion();

		Optional<TargetServer> targetServerFromDb = getTargetServer(targetServerId);
		TargetServer targetServer = targetServerFromDb.orElseThrow(EntityNotFoundException::new);

		Resource resource = appResourceService.getResource(appTemplate, version);

		try {
			// TODO : file로 가져올 수 없을 때의 처리 필요

			File file = resource.getFile();

			// passwordless SSH
			String host = targetServer.getAddress();
			int port = targetServer.getSshPort();

			SshClient sshClient = new SshClient(host, port);
			sshClient.connect(targetServer.getUsername(), targetServer.getPassword());

			String agentPath = targetServer.getAgentInstallPath();
			sshClient.mkdir(agentPath);

			String remoteFilename = Paths.get(agentPath, file.getName()).toString();
			sshClient.scpTo(file, remoteFilename);

			String startCommand = appTemplate.getStartCommandLine();
			if (StringUtils.isBlank(startCommand)) {
				startCommand = "nohup java -jar " + file.getName() + " --server.port=8080 1>agent.out 2>&1 &";
			}

			try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
					PrintStream printStream = new PrintStream(baos)) {
				sshClient.exec(agentPath, startCommand, printStream, 10 * 1000);
				String output = baos.toString("UTF-8");
				log.info("exec : {}", output);
			}

		} catch (Exception e) {
			throw Exceptions.newException(LampErrorCode.AGENT_INSTALL_FAILED, e);
		}


	}

	public Page<TargetServerDto> getTargetServerList(Pageable pageable) {
		Page<TargetServer> page = targetServerRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, TargetServerDto.class);
	}


}
