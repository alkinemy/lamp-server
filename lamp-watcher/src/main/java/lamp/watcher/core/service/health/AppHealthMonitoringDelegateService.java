package lamp.watcher.core.service.health;

import lamp.watcher.core.domain.*;
import lamp.watcher.core.service.AppEventService;
import lamp.watcher.core.service.health.AppHealthMonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppHealthMonitoringDelegateService {

	@Autowired
	private AppEventService appEventService;

	@Autowired
	private AppHealthMonitoringService appHealthMonitoringService;

	public void monitoring(WatchedApp watchedApp, AppHealth appHealth) {
		try {
			appHealthMonitoringService.monitoring(watchedApp, appHealth);
		} catch (Throwable e) {
			log.warn("Health Monitoring Failed ", e);
			AppEvent appEvent = AppEvent.of(watchedApp, AppEventName.HEALTH_MONITORING_FAILED, AppEventLevel.WARN, e);
			appEventService.publish(appEvent);
		}
	}

}
