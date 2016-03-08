package lamp.watcher.monitoring.collection.service;

import lamp.common.collection.metrics.TargetMetrics;
import lamp.common.monitoring.MonitoringTarget;
import lamp.watcher.core.service.MonitoringMetricsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class MonitoringMetricsCollectionService {

	@Autowired
	private MetricsLoaderService healthLoaderService;

	@Autowired
	private MonitoringMetricsService monitoringMetricsService;

	@Async
	public void monitoring(MonitoringTarget monitoringTarget) {
		TargetMetrics targetMetrics = healthLoaderService.getMetrics(monitoringTarget);
		if (targetMetrics != null) {
			monitoringMetricsService.monitoring(monitoringTarget, targetMetrics);
		}
	}

}
