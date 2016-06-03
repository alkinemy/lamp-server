package lamp.admin.domain.agent.service;


import lamp.admin.domain.agent.model.*;
import lamp.admin.domain.agent.repository.TargetServerRepository;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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

	public List<TargetServerDto> getTargetServerDtoList() {
		List<TargetServer> list = targetServerRepository.findAll(new Sort("name"));
		return smartAssembler.assemble(list, TargetServerDto.class);
	}

	public Collection<TargetServer> getTargetServerList() {
		return targetServerRepository.findAll();
	}

	public TargetServerDto getTargetServerDto(String id) {
		TargetServer targetServer = getTargetServer(id);
		return smartAssembler.assemble(targetServer, TargetServerDto.class);
	}

	public TargetServer getTargetServer(String id) {
		return getTargetServerOptional(id).orElseThrow(() -> Exceptions.newException(LampErrorCode.TARGET_SERVER_NOT_FOUND, id));
	}

	public List<TargetServerDto> getTargetServerDtos(List<String> targetServerIds) {
		return targetServerRepository.findAllByIdIn(targetServerIds);
	}

	public Optional<TargetServer> getTargetServerOptional(String id) {
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

	public TargetServerUpdateForm getTargetServerUpdateForm(String id) {
		TargetServer targetServer = getTargetServer(id);
		return smartAssembler.assemble(targetServer, TargetServerUpdateForm.class);
	}

	@Transactional
	public TargetServer updateTargetServer(TargetServerUpdateForm editForm) {
		TargetServer targetServer = getTargetServer(editForm.getId());
		smartAssembler.populate(editForm, targetServer);
		return targetServerRepository.save(targetServer);
	}

	@Transactional
	public void deleteTargetServer(String id) {
		TargetServer targetServer = getTargetServer(id);
		Optional<Agent> agent = agentService.getAgentByTargetServerIdOptional(targetServer.getId());
		Exceptions.throwsException(agent.isPresent(), LampErrorCode.TARGET_SERVER_DELETE_FAILED_AGENT_EXIST, id);
		targetServerRepository.delete(targetServer);
	}



}
