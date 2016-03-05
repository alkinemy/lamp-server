package lamp.collector.core.service;

import lamp.collector.core.domain.*;
import lamp.collector.core.service.health.HealthCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HealthCollectionFacadeService {

	@Autowired
	private HealthCollectionService healthCollectionService;

	@Autowired
	private EventService eventService;

	public void collection(CollectionTarget app) {
		try {
			healthCollectionService.monitoring(app);
		} catch (Throwable e) {
			log.warn("Health Collection Failed ", e);
			Event event = Event.of(app, EventName.HEALTH_COLLECTION_FAILED, AppEventLevel.WARN, e);
			eventService.publish(event);
		}
	}


}
