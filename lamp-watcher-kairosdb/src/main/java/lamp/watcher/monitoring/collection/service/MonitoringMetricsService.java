package lamp.watcher.monitoring.collection.service;

import lamp.common.metrics.TargetMetrics;
import lamp.common.monitoring.MonitoringTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class MonitoringMetricsService {

	@Autowired
	private MetricsLoaderService healthLoaderService;

	@Autowired
	private lamp.watcher.core.service.MonitoringMetricsService monitoringMetricsService;

	@Async
	public void monitoring(MonitoringTarget monitoringTarget) {
		TargetMetrics targetMetrics = healthLoaderService.getMetrics(monitoringTarget);
		if (targetMetrics != null) {
			monitoringMetricsService.monitoring(monitoringTarget, targetMetrics);
		}
	}

}
