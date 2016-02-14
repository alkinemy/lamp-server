package lamp.admin.core.agent.service;


import lamp.admin.core.agent.domain.Agent;
import lamp.admin.core.agent.domain.AgentDto;
import lamp.admin.core.agent.domain.AgentRegisterForm;
import lamp.admin.core.agent.domain.TargetServer;
import lamp.admin.core.agent.repository.AgentRepository;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.StringUtils;
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
public class AgentService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private TargetServerService targetServerService;


	public Agent getAgent(String id) {
		Optional<Agent> agentOptional = getAgentOptional(id);
		return agentOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, id));
	}

	public Optional<Agent> getAgentOptional(String id) {
		return Optional.ofNullable(agentRepository.findOne(id));
	}

	public Agent getAgentByTargetServerId(Long id) {
		Optional<Agent> agentOptional = getAgentByTargetServerIdOptional(id);
		return agentOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND_BY_TARGET_SERVER, id));
	}

	public Optional<Agent> getAgentByTargetServerIdOptional(Long id) {
		return Optional.ofNullable(agentRepository.findOneByTargetServerId(id));
	}

	public AgentDto getAgentDto(String id) {
		Optional<Agent> agentFromDb = getAgentOptional(id);
		if (agentFromDb.isPresent()) {
			return smartAssembler.assemble(agentFromDb.get(), AgentDto.class);
		} else {
			throw Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, id);
		}
	}

	public Page<AgentDto> getAgentDtoList(Pageable pageable) {
		Page<Agent> page = agentRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, AgentDto.class);
	}

	public Collection<Agent> getAgentList() {
		return agentRepository.findAll();
	}

	@Transactional
	public Agent register(AgentRegisterForm form) {
		String id = form.getId();
		Optional<Agent> agentFromDb = getAgentOptional(id);

		boolean duplicated = agentFromDb
				.filter(a -> !StringUtils.equals(a.getSecretKey(), form.getSecretKey()))
				.isPresent();
		Exceptions.throwsException(duplicated, LampErrorCode.DUPLICATED_AGENT_ID, form.getId());

		if (agentFromDb.isPresent()) {
			return update(form, agentFromDb.get());
		} else {
			return insert(form);
		}
	}


	@Transactional
	public Agent insert(AgentRegisterForm form) {
		Agent agent = smartAssembler.assemble(form, Agent.class);

		Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerByHostname(agent.getHostname());
		TargetServer targetServer = upsertTargetServer(agent, targetServerFromDb);
		agent.setTargetServer(targetServer);

		return agentRepository.save(agent);
	}


	@Transactional
	public Agent update(AgentRegisterForm form, Agent agent) {
		BeanUtils.copyProperties(form, agent, "id", "secretKey");

		if (!agent.getTargetServer().getHostname().equals(agent.getHostname())) {
			Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerByHostname(agent.getHostname());
			TargetServer targetServer = upsertTargetServer(agent, targetServerFromDb);
			agent.setTargetServer(targetServer);
		}
		return agent;
	}

	private TargetServer upsertTargetServer(Agent agent, Optional<TargetServer> targetServerFromDb) {
		TargetServer targetServer;
		if (targetServerFromDb.isPresent()) {
			targetServer = targetServerFromDb.get();
			targetServer.setAddress(agent.getAddress());
			targetServer.setAgentInstalled(true);
			targetServer.setAgentInstallPath(agent.getAppDirectory());
			targetServer.setAgentHealthUrl(agent.getHealthUrl());
		} else {
			targetServer = targetServerService.insertTargetServer(smartAssembler.assemble(agent, TargetServer.class));
		}
		return targetServer;
	}

	@Transactional
	public void deregister(String id) {
		Optional<Agent> agentFromDb = getAgentOptional(id);
		agentFromDb.ifPresent(this::delete);
	}

	@Transactional
	public void delete(Agent agent) {
		agentRepository.delete(agent);
	}


}
