package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.dto.TargetServerCreateForm;
import lamp.server.aladin.core.dto.TargetServerDto;
import lamp.server.aladin.core.repository.TargetServerRepository;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class TargetServerService {

	@Autowired
	private TargetServerRepository targetServerRepository;

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

	public Page<TargetServerDto> getTargetServerList(Pageable pageable) {
		Page<TargetServer> page = targetServerRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, TargetServerDto.class);
	}


}
