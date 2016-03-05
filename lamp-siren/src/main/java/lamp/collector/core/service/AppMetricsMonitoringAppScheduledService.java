package lamp.collector.core.service;

import lamp.admin.utils.BooleanUtils;
import lamp.collector.core.domain.WatchedApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class AppMetricsMonitoringAppScheduledService {

	@Autowired
	private WatchedAppService watchedAppService;

	@Autowired
	private AppMetricsMonitoringAppService appMetricsMonitoringAppService;

	public void monitoring() {
		Collection<WatchedApp> watchedApps = watchedAppService.getWatchedAppListForMetricsCollection();
		watchedApps.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::metricsMonitoring);
	}

	protected void metricsMonitoring(WatchedApp app) {
		try {
			appMetricsMonitoringAppService.monitoring(app);
		} catch (Exception e) {
			log.warn("Metrics Collection Failed", e);
		}
	}

}
