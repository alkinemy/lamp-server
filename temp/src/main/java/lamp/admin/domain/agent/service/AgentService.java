package lamp.admin.domain.agent.service;


import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.model.AgentDto;
import lamp.admin.domain.agent.model.AgentRegisterForm;
import lamp.admin.domain.agent.model.TargetServer;
import lamp.admin.domain.agent.repository.AgentRepository;
import lamp.admin.domain.base.model.JavaVirtualMachine;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.support.agent.AgentClient;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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
	@Autowired
	private AgentClient agentClient;

	public Agent getAgent(String id) {
		Optional<Agent> agentOptional = getAgentOptional(id);
		return agentOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, id));
	}

	public Optional<Agent> getAgentOptional(String id) {
		return Optional.ofNullable(agentRepository.findOne(id));
	}

	public Agent getAgentByTargetServerId(String id) {
		Optional<Agent> agentOptional = getAgentByTargetServerIdOptional(id);
		return agentOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND_BY_TARGET_SERVER, id));
	}

	public Optional<Agent> getAgentByTargetServerIdOptional(String id) {
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

	public List<JavaVirtualMachine> getJavaVmList(String id) {
		Agent agent = getAgent(id);
		return agentClient.getVmList(agent);
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

		Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerOptional(agent.getId());
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
			smartAssembler.populate(agent, targetServer);
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
