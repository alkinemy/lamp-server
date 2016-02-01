package lamp.server.aladin.core.service;

import lamp.server.aladin.api.dto.AgentEventForm;
import lamp.server.aladin.core.domain.AgentEvent;
import lamp.server.aladin.core.repository.AgentEventRepository;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
public class AgentEventService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private AgentEventRepository agentEventRepository;

	@Transactional
	public AgentEvent insertAgentEvent(String id, AgentEventForm form) {
		AgentEvent agentEvent = smartAssembler.assemble(form, AgentEvent.class);
		agentEvent.setAgentId(id);
		agentEvent.setEventTime(LocalDateTime.ofInstant(form.getEventTime().toInstant(), ZoneId.systemDefault()));
		return agentEventRepository.save(agentEvent);
	}

}
