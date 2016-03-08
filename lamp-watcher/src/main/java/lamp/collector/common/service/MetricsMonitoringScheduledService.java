package lamp.collector.common.service;

import lamp.common.utils.BooleanUtils;
import lamp.collector.common.domain.MonitoringTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class MetricsMonitoringScheduledService {

	@Autowired
	private MonitoringTargetService watchedAppService;

	@Autowired
	private AppMetricsMonitoringAppService appMetricsMonitoringAppService;

	public void monitoring() {
		Collection<MonitoringTarget> watchedApps = watchedAppService.getWatchedAppListForMetricsCollection();
		watchedApps.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::metricsMonitoring);
	}

	protected void metricsMonitoring(MonitoringTarget app) {
		try {
			appMetricsMonitoringAppService.monitoring(app);
		} catch (Exception e) {
			log.warn("Metrics Collection Failed", e);
		}
	}

}
