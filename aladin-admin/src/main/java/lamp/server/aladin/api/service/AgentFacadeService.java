package lamp.server.aladin.api.service;

import lamp.server.aladin.api.dto.AgentDto;
import lamp.server.aladin.api.dto.AgentRegisterForm;
import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.service.AgentService;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgentFacadeService {

	@Autowired
	private AgentService agentService;

	@Autowired
	private SmartAssembler smartAssembler;

	@Transactional
	public AgentDto insert(AgentRegisterForm form) {
		Agent saved = agentService.register(form);
		return smartAssembler.assemble(saved, AgentDto.class);
	}

}
