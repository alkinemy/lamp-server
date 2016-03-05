package lamp.collector.core.service;

import lamp.collector.core.domain.AppEvent;
import lamp.collector.core.domain.AppEventLevel;
import lamp.collector.core.domain.AppEventName;
import lamp.collector.core.domain.TargetApp;
import lamp.collector.core.service.metrics.AppMetricsCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppMetricsCollectionFacadeService {

	@Autowired
	private AppMetricsCollectionService appMetricsCollectionService;

	@Autowired
	private AppEventService appEventService;

	public void collection(TargetApp targetApp) {
		try {
			appMetricsCollectionService.collection(targetApp);
		} catch (Throwable e) {
			log.warn("Metrics Collection Failed ", e);
			AppEvent appEvent = AppEvent.of(targetApp, AppEventName.METRICS_COLLECTION_FAILED, AppEventLevel.WARN, e);
			appEventService.publish(appEvent);
		}
	}

}
