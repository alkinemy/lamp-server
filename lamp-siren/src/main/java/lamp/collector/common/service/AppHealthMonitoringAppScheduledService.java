package lamp.collector.common.service;

import lamp.common.utils.BooleanUtils;
import lamp.collector.common.domain.WatchedApp;
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
