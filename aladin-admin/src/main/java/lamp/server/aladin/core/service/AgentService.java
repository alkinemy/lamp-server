package lamp.server.aladin.core.service;

import lamp.server.aladin.api.dto.AgentForm;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.exception.ErrorCode;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.exception.MessageException;
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

	@Transactional
	public Agent insert(AgentForm form) {
		String hostname = form.getHostname();
		Optional<Agent> agentFromDb = agentRepository.findOneByHostname(hostname);
		Exceptions.throwsException(agentFromDb.isPresent(), LampErrorCode.DUPLICATED_HOSTNAME);

		// FIXME id로 중복 체크 추가

		Agent agent = smartAssembler.assemble(form, Agent.class);
		Optional<TargetServer> targetServerFromDb = targetServerService.getTargetServerByHostname(hostname);

		TargetServer targetServer = targetServerFromDb.orElseGet(
				() -> targetServerService.insertTargetServer(smartAssembler.assemble(agent, TargetServer.class)));
		agent.setTargetServer(targetServer);

		return agentRepository.save(agent);
	}

}
