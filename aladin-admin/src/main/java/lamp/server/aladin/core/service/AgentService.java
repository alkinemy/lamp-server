package lamp.server.aladin.core.service;

import lamp.server.aladin.api.dto.AgentForm;
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

	@Transactional
	public void insert(AgentForm form) {
		Optional<Agent> found = agentRepository.findOneByHostname(form.getHostname());
		if (found != null) {

		}
		Agent agent = smartAssembler.assemble(form, Agent.class);
		agentRepository.save(agent);
	}

}
