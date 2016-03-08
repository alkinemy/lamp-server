package lamp.collector.common.service;

import lamp.common.utils.BooleanUtils;
import lamp.collector.common.domain.MonitoringTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class HealthMonitoringScheduledService {

	@Autowired
	private MonitoringTargetService watchedAppService;

	@Autowired
	private AppHealthMonitoringAppService appHealthMonitoringAppService;

	public void monitoring() {
		Collection<MonitoringTarget> watchedApps = watchedAppService.getWatchedAppListForHealthCollection();
		watchedApps.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::healthMonitoring);
	}

	protected void healthMonitoring(MonitoringTarget app) {
		try {
			appHealthMonitoringAppService.monitoring(app);
		} catch (Exception e) {
			log.warn("Health Collection Failed", e);
		}
	}

}
