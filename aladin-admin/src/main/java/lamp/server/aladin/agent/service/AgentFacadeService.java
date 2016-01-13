package lamp.server.aladin.agent.service;

import lamp.server.aladin.core.dto.AgentDto;
import lamp.server.aladin.agent.dto.AgentRegisterForm;
import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.service.AgentService;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AgentFacadeService {

	@Autowired
	private AgentService agentService;

	@Autowired
	private SmartAssembler smartAssembler;

	@Transactional
	public AgentDto register(AgentRegisterForm form) {
		Agent agentFromDb = agentService.register(form);
		return smartAssembler.assemble(agentFromDb, AgentDto.class);
	}

	@Transactional
	public void deregister(String id) {
		agentService.deregister(id);
	}

}
