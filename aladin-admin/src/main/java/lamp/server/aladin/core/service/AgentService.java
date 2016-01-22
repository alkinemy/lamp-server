package lamp.server.aladin.core.service;

import lamp.server.aladin.api.dto.AgentRegisterForm;
import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.dto.AgentDto;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.repository.AgentRepository;
import lamp.server.aladin.utils.StringUtils;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


	public Optional<Agent> getAgent(String id) {
		return Optional.ofNullable(agentRepository.findOne(id));
	}

	public AgentDto getAgentDto(String id) {
		Optional<Agent> agentFromDb = getAgent(id);
		if (agentFromDb.isPresent()) {
			return smartAssembler.assemble(agentFromDb.get(), AgentDto.class);
		} else {
			throw Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, id);
		}
	}

	public Page<AgentDto> getAgentList(Pageable pageable) {
		Page<Agent> page = agentRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, AgentDto.class);
	}

	@Transactional
	public Agent register(AgentRegisterForm form) {
		String id = form.getId();
		Optional<Agent> agentFromDb = getAgent(id);

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
		TargetServer targetServer = targetServerFromDb.orElseGet(
				() -> targetServerService.insertTargetServer(smartAssembler.assemble(agent, TargetServer.class)));
		agent.setTargetServer(targetServer);

		return agentRepository.save(agent);
	}


	@Transactional
	public Agent update(AgentRegisterForm form, Agent agent) {
		BeanUtils.copyProperties(form, agent, "id", "secretKey");

		if (!agent.getTargetServer().getHostname().equals(agent.getHostname())) {
			Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerByHostname(agent.getHostname());
			TargetServer targetServer = targetServerFromDb.orElseGet(
					() -> targetServerService.insertTargetServer(smartAssembler.assemble(agent, TargetServer.class)));
			agent.setTargetServer(targetServer);
		}
		return agent;
	}

	@Transactional
	public void deregister(String id) {
		Optional<Agent> agentFromDb = getAgent(id);
		agentFromDb.ifPresent(this::delete);
	}

	@Transactional
	public void delete(Agent agent) {
		agentRepository.delete(agent);
	}

}
