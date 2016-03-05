package lamp.collector.core.service;

import lamp.collector.core.domain.*;
import lamp.collector.core.service.health.AppHealthCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppHealthCollectionFacadeService {

	@Autowired
	private AppHealthCollectionService appHealthCollectionService;

	@Autowired
	private AppEventService appEventService;

	public void collection(TargetApp app) {
		try {
			appHealthCollectionService.monitoring(app);
		} catch (Throwable e) {
			log.warn("Health Collection Failed ", e);
			AppEvent appEvent = AppEvent.of(app, AppEventName.HEALTH_COLLECTION_FAILED, AppEventLevel.WARN, e);
			appEventService.publish(appEvent);
		}
	}


}
