package lamp.collector.common.service;


import lamp.common.utils.assembler.SmartAssembler;
import lamp.collector.common.domain.AppEvent;
import lamp.collector.common.domain.AppEventForm;
import lamp.collector.common.repository.AppEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
public class AppEventService {

	@Autowired
	private SmartAssembler smartAssembler;


	@Autowired
	private AppEventRepository appEventRepository;

	@Transactional
	public AppEvent insertAppEvent(String id, AppEventForm form) {
		AppEvent appEvent = smartAssembler.assemble(form, AppEvent.class);
		appEvent.setAgentId(id);
		appEvent.setTime(LocalDateTime.ofInstant(form.getEventTime().toInstant(), ZoneId.systemDefault()));
		return appEventRepository.save(appEvent);
	}

	public void publish(AppEvent appEvent) {
		log.info("appEvent = {}", appEvent);
	}

}
