package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AppFile;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import lamp.server.aladin.core.dto.TargetServerCreateForm;
import lamp.server.aladin.core.dto.TargetServerDto;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.exception.EntityNotFoundException;
import lamp.server.aladin.core.repository.TargetServerRepository;
import lamp.server.aladin.core.support.ssh.SshClient;
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
	private AppFileService appFileService;

	@Autowired
	private AppFileDownloadService appFileDownloadService;

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

	public void installAgent(Long targetServerId, Long appFileId) {
		Optional<TargetServer> targetServerFromDb = getTargetServer(targetServerId);
		targetServerFromDb.orElseThrow(EntityNotFoundException::new);

		Optional<AppFile> agentAppFile = appFileService.getAppFile(appFileId);
		agentAppFile.orElseThrow(() -> Exceptions.newException(LampErrorCode.ENTITY_NOT_FOUND));

		File file = appFileDownloadService.download(agentAppFile.get());

		TargetServer targetServer = targetServerFromDb.get();
		// passwordless SSH
		String host = targetServer.getAddress();
		int port = targetServer.getSshPort();
		SshClient sshClient = new SshClient(host, port);
		String agentPath = targetServer.getAgentInstallPath();
		sshClient.mkdir(agentPath);

		String remoteFilename = Paths.get(agentPath, file.getName()).toString();
		sshClient.scpTo(file, remoteFilename);

		String agentStartCmd = "nohup java -jar " + file.getName() + " --server.port=8080 1 > nohup.out 2 > %1 &";
		sshClient.exec(agentPath, agentStartCmd);
	}

	public Page<TargetServerDto> getTargetServerList(Pageable pageable) {
		Page<TargetServer> page = targetServerRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, TargetServerDto.class);
	}


}
