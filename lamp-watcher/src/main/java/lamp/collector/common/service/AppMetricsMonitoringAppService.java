package lamp.collector.common.service;

import lamp.common.utils.BooleanUtils;
import lamp.collector.common.domain.*;
import lamp.collector.common.service.metrics.AppMetricsMonitoringDelegateService;
import lamp.collector.common.service.metrics.collector.AppMetricsCollectorService;
import lamp.collector.common.service.metrics.export.AppMetricsExportFacadeService;
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
	public void monitoring(MonitoringTarget watchedApp) {
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
