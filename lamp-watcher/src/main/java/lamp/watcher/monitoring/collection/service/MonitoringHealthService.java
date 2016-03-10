package lamp.watcher.monitoring.collection.service;

import lamp.common.metrics.TargetHealth;
import lamp.common.monitoring.MonitoringTarget;
import lamp.watcher.core.service.MonitoringHealthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class MonitoringHealthService {

	@Autowired
	private HealthLoaderService healthLoaderService;

	@Autowired
	private lamp.watcher.core.service.MonitoringHealthService monitoringHealthService;

	@Async
	public void monitoring(MonitoringTarget monitoringTarget) {
		TargetHealth targetHealth = healthLoaderService.getHealth(monitoringTarget);
		if (targetHealth != null) {
			monitoringHealthService.monitoring(monitoringTarget, targetHealth);
		}
	}

}
