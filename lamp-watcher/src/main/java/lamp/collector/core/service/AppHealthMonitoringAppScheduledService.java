package lamp.collector.core.service;

import lamp.admin.utils.BooleanUtils;
import lamp.collector.core.domain.WatchedApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class AppHealthMonitoringAppScheduledService {

	@Autowired
	private WatchedAppService watchedAppService;

	@Autowired
	private AppHealthMonitoringAppService appHealthMonitoringAppService;

	public void monitoring() {
		Collection<WatchedApp> watchedApps = watchedAppService.getWatchedAppListForHealthCollection();
		watchedApps.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::healthMonitoring);
	}

	protected void healthMonitoring(WatchedApp app) {
		try {
			appHealthMonitoringAppService.monitoring(app);
		} catch (Exception e) {
			log.warn("Health Collection Failed", e);
		}
	}

}
