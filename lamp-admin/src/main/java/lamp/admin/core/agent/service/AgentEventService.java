package lamp.admin.core.agent.service;

import lamp.admin.core.agent.domain.AgentEvent;
import lamp.admin.core.agent.domain.AgentEventDto;
import lamp.admin.core.agent.domain.AgentEventForm;
import lamp.admin.core.agent.repository.AgentEventRepository;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<AgentEventDto> getAgentEventDtoList(Pageable pageable) {
		Page<AgentEvent> page = agentEventRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, AgentEventDto.class);
	}

	@Transactional
	public AgentEvent insertAgentEvent(String id, AgentEventForm form) {
		AgentEvent agentEvent = smartAssembler.assemble(form, AgentEvent.class);
		agentEvent.setAgentId(id);
		agentEvent.setAgentInstanceId(form.getInstanceId());
		agentEvent.setAgentInstanceEventSequence(form.getInstanceEventSequence());
		agentEvent.setEventTime(LocalDateTime.ofInstant(form.getEventTime().toInstant(), ZoneId.systemDefault()));
		return agentEventRepository.save(agentEvent);
	}

}
