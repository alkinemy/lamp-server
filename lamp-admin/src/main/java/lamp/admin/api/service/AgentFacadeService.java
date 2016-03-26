package lamp.admin.api.service;

import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.model.AgentDto;
import lamp.admin.domain.agent.model.AgentRegisterForm;
import lamp.admin.domain.agent.service.AgentService;
import lamp.common.utils.assembler.SmartAssembler;
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
