package lamp.collector.core.service;


import lamp.admin.utils.assembler.SmartAssembler;
import lamp.collector.core.domain.Event;
import lamp.collector.core.domain.AppEventForm;
import lamp.collector.core.repository.AppEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
public class EventService {

	@Autowired
	private SmartAssembler smartAssembler;


	@Autowired
	private AppEventRepository appEventRepository;

	@Transactional
	public Event insertAppEvent(String id, AppEventForm form) {
		Event event = smartAssembler.assemble(form, Event.class);
		event.setSourceId(id);
		event.setTime(LocalDateTime.ofInstant(form.getEventTime().toInstant(), ZoneId.systemDefault()));
		return appEventRepository.save(event);
	}

	public void publish(Event event) {
		log.info("event = {}", event);
	}

}
