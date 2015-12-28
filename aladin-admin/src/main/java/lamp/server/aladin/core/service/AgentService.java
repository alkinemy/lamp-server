package lamp.server.aladin.core.service;

import lamp.server.aladin.utils.assembler.SmartAssembler;
import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.repository.AgentRepository;
import lamp.server.aladin.core.service.command.LampClientCreateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgentService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private AgentRepository agentRepository;

	@Transactional
	public void handle(LampClientCreateCommand command) {
		Agent agent = smartAssembler.assemble(command, Agent.class);
		agentRepository.save(agent);
	}

//	@Transactional
//	public void handle(LampClientCreateOrUpdateCommand command) {
//		String id = command.getId();
//		Agent agent = agentRepository.findOne(id);
//		if (agent == null) {
//			agent = smartAssembler.assemble(command, Agent.class);
//			agentRepository.save(agent);
//		} else {
//			populateProperties(agent, command);
//		}
//	}
//
//	@Transactional
//	public void handle(LampClientDeleteCommand command) {
//		agentRepository.delete(command.getId());
//	}
//
//	protected void populateProperties(Agent agent, LampClientCreateOrUpdateCommand command) {
//		BeanUtils.copyProperties(agent, command, Agent.class);
//	}

}
