package lamp.server.aladin.core.service;

import lamp.server.aladin.api.dto.AgentDeregisterForm;
import lamp.server.aladin.api.dto.AgentRegisterForm;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.utils.StringUtils;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

	@Transactional
	public Agent insert(Agent agent) {
		Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerByHostname(agent.getHostname());

		TargetServer targetServer = targetServerFromDb.orElseGet(
				() -> targetServerService.insertTargetServer(smartAssembler.assemble(agent, TargetServer.class)));
		agent.setTargetServer(targetServer);

		return agentRepository.save(agent);
	}

	@Transactional
	public void delete(Agent agent) {
		TargetServer targetServer = agent.getTargetServer();
		targetServer.setAgentInstalled(false);
		agentRepository.delete(agent);
	}

	@Transactional
	public Agent register(AgentRegisterForm form) {
		String id = form.getId();
		Optional<Agent> agentFromDb = getAgent(id);

		boolean duplicated = agentFromDb
				.filter(a -> !StringUtils.equals(a.getSecretKey(), form.getSecretKey()))
				.isPresent();
		Exceptions.throwsException(duplicated, LampErrorCode.DUPLICATED_AGENT_ID, form.getId());

		agentFromDb.ifPresent(this::delete);

		Agent agent = smartAssembler.assemble(form, Agent.class);
		return insert(agent);
	}

	@Transactional
	public void deregister(AgentDeregisterForm form) {
		String id = form.getId();
		Optional<Agent> agentFromDb = getAgent(id);

		agentFromDb
				.filter(a -> StringUtils.equals(a.getSecretKey(), form.getSecretKey()))
				.ifPresent(this::delete);
	}

}
