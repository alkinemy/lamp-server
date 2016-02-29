package lamp.watcher.core.service;

import lamp.admin.utils.BooleanUtils;
import lamp.watcher.core.domain.*;
import lamp.watcher.core.service.metrics.AppMetricsMonitoringDelegateService;
import lamp.watcher.core.service.metrics.collector.AppMetricsCollectorService;
import lamp.watcher.core.service.metrics.export.AppMetricsExportFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;


@Slf4j
public class AppMetricsMonitoringAppService {

	@Autowired
	private AppEventService appEventService;

	@Autowired
	private AppMetricsCollectorService appMetricsCollectorService;

	@Autowired
	private AppMetricsMonitoringDelegateService appMetricsMonitoringDelegateService;

	@Autowired
	private AppMetricsExportFacadeService appMetricsExportFacadeService;

	@Async
	public void monitoring(WatchedApp watchedApp) {
		if (!BooleanUtils.isTrue(watchedApp.getMetricsCollectionEnabled())) {
			return;
		}

		try {
			AppMetrics appMetrics = appMetricsCollectorService.getMetrics(watchedApp);

			appMetricsMonitoringDelegateService.monitoring(watchedApp, appMetrics);
			appMetricsExportFacadeService.export(watchedApp, appMetrics);

		} catch (Throwable e) {
			log.warn("Metrics Collection failed", e);
			AppEvent appEvent = AppEvent.of(watchedApp, AppEventName.METRICS_COLLECTION_FAILED, AppEventLevel.WARN, e);
			appEventService.publish(appEvent);
		}
	}

}
