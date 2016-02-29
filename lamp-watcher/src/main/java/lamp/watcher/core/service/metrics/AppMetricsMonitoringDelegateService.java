package lamp.watcher.core.service.metrics;

import lamp.watcher.core.domain.*;
import lamp.watcher.core.service.AppEventService;
import lamp.watcher.core.service.metrics.AppMetricsMonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppMetricsMonitoringDelegateService {

	@Autowired
	private AppEventService appEventService;

	@Autowired
	private AppMetricsMonitoringService appMetricsMonitoringService;

	public void monitoring(WatchedApp watchedApp, AppMetrics appMetrics) {
		try {
			appMetricsMonitoringService.monitoring(watchedApp, appMetrics);
		} catch (Throwable e) {
			log.warn("Metrics Monitoring Failed ", e);
			AppEvent appEvent = AppEvent.of(watchedApp, AppEventName.METRICS_MONITORING_FAILED, AppEventLevel.WARN, e);
			appEventService.publish(appEvent);
		}
	}

}
