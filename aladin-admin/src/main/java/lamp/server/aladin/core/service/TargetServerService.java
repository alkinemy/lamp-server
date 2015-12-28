package lamp.server.aladin.core.service;

import lamp.server.aladin.utils.assembler.SmartAssembler;
import lamp.server.aladin.core.controller.TargetServerCreateForm;
import lamp.server.aladin.core.controller.TargetServerDto;
import lamp.server.aladin.core.domain.AgentJar;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.exception.EntityNotFoundException;
import lamp.server.aladin.core.repository.TargetServerRepository;
import lamp.server.aladin.core.service.ssh.SshClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class TargetServerService {

	@Autowired
	private TargetServerRepository targetServerRepository;

	@Autowired
	private AgentJarService agentJarService;

	@Autowired
	private FileDownloadService fileDownloadService;

	@Autowired
	private SmartAssembler smartAssembler;

	public Optional<TargetServer> getTargetServer(Long id) {
		TargetServer targetServer = targetServerRepository.getOne(id);
		return Optional.ofNullable(targetServer);
	}

	@Transactional
	public void insertTargetServer(TargetServerCreateForm editForm) {
		TargetServer targetServer = smartAssembler.assemble(editForm, TargetServer.class);
		targetServerRepository.save(targetServer);
	}

	public void installAgent(Long targetServerId, Long agentJarId) {
		Optional<TargetServer> targetServer = getTargetServer(targetServerId);
		targetServer.orElseThrow(EntityNotFoundException::new);

		Optional<AgentJar> agentJar = agentJarService.getAgentJar(agentJarId);
		agentJar.orElseThrow(EntityNotFoundException::new);

		File file = fileDownloadService.download(agentJar.get());

		// passwordless SSH
		SshClient sshClient = null;
		String agentPath = targetServer.get().getAgentPath();
		sshClient.mkdir(agentPath);

		String remoteFilename = Paths.get(agentPath, file.getName()).toString();
		sshClient.scpTo(file, remoteFilename);

		String agentStartCmd = agentJar.get().getStartCmd();
		sshClient.exec(agentPath, agentStartCmd);
	}

	public Page<TargetServerDto> getTargetServerList(Pageable pageable) {
		Page<TargetServer> page = targetServerRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, TargetServerDto.class);
	}

}
