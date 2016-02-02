package lamp.server.watch.core.service;


import lamp.server.aladin.utils.assembler.SmartAssembler;
import lamp.server.watch.core.domain.AppEvent;
import lamp.server.watch.core.domain.AppEventForm;
import lamp.server.watch.core.repository.AppEventRepository;
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
		appEvent.setEventTime(LocalDateTime.ofInstant(form.getEventTime().toInstant(), ZoneId.systemDefault()));
		return appEventRepository.save(appEvent);
	}

}
