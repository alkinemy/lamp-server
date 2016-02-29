package lamp.watcher.core.service;

import lamp.admin.utils.BooleanUtils;
import lamp.watcher.core.domain.*;
import lamp.watcher.core.service.health.AppHealthMonitoringDelegateService;
import lamp.watcher.core.service.health.collector.AppHealthCollectorService;
import lamp.watcher.core.service.health.export.AppHealthExportFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class AppHealthMonitoringAppService {

	@Autowired
	private AppEventService appEventService;

	@Autowired
	private AppHealthCollectorService appHealthCollectorService;

	@Autowired
	private AppHealthMonitoringDelegateService appHealthMonitoringDelegateService;

	@Autowired
	private AppHealthExportFacadeService appHealthExportFacadeService;

	@Async
	public void monitoring(WatchedApp watchedApp) {
		if (!BooleanUtils.isTrue(watchedApp.getHealthCollectionEnabled())) {
			return;
		}

		try {
			AppHealth health = appHealthCollectorService.getHealth(watchedApp);

			appHealthMonitoringDelegateService.monitoring(watchedApp, health);
			appHealthExportFacadeService.export(watchedApp, health);
		} catch(Throwable e) {
			log.warn("Health Monitoring failed", e);

			AppEvent appEvent = AppEvent.of(watchedApp, AppEventName.HEALTH_COLLECTION_FAILED, AppEventLevel.WARN, e);
			appEventService.publish(appEvent);
		}

	}


}
