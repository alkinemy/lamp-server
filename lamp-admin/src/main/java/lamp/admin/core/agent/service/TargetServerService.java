package lamp.admin.core.agent.service;


import lamp.admin.core.agent.domain.*;
import lamp.admin.core.agent.repository.TargetServerRepository;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class TargetServerService {

	@Autowired
	private TargetServerRepository targetServerRepository;

	@Autowired
	private AgentService agentService;

	@Autowired
	private SmartAssembler smartAssembler;

	public Page<TargetServerDto> getTargetServerDtoList(Pageable pageable) {
		Page<TargetServer> page = targetServerRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, TargetServerDto.class);
	}

	public Collection<TargetServer> getTargetServerList() {
		return targetServerRepository.findAll();
	}

	public TargetServerDto getTargetServerDto(Long id) {
		TargetServer targetServer = getTargetServer(id);
		return smartAssembler.assemble(targetServer, TargetServerDto.class);
	}

	public TargetServer getTargetServer(Long id) {
		return getTargetServerOptional(id).orElseThrow(() -> Exceptions.newException(LampErrorCode.TARGET_SERVER_NOT_FOUND, id));
	}

	public Optional<TargetServer> getTargetServerOptional(Long id) {
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

	public TargetServerUpdateForm getTargetServerUpdateForm(Long id) {
		TargetServer targetServer = getTargetServer(id);
		return smartAssembler.assemble(targetServer, TargetServerUpdateForm.class);
	}

	@Transactional
	public TargetServer updateTargetServer(TargetServerUpdateForm editForm) {
		TargetServer targetServer = getTargetServer(editForm.getId());
		BeanUtils.copyProperties(editForm, targetServer);
		return targetServerRepository.save(targetServer);
	}

	@Transactional
	public void deleteTargetServer(Long id) {
		TargetServer targetServer = getTargetServer(id);
		Optional<Agent> agent = agentService.getAgentByTargetServerIdOptional(targetServer.getId());
		Exceptions.throwsException(agent.isPresent(), LampErrorCode.TARGET_SERVER_DELETE_FAILED_AGENT_EXIST, id);
		targetServerRepository.delete(targetServer);
	}


}
