package lamp.server.aladin.api.service;

import lamp.server.aladin.api.dto.AgentDto;
import lamp.server.aladin.api.dto.AgentForm;
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
	public AgentDto insert(AgentForm form) {
		Agent saved = agentService.insert(form);
		return smartAssembler.assemble(saved, AgentDto.class);
	}

}
