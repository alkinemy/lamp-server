package lamp.watcher.monitoring.collection.service;

import lamp.common.monitoring.MonitoringTarget;
import lamp.common.utils.BooleanUtils;
import lamp.watcher.core.service.MonitoringTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class MonitoringMetricsScheduledService {

	@Autowired
	private MonitoringTargetService monitoringTargetService;

	@Autowired
	private MonitoringMetricsService monitoringMetricsCollectionService;

	public void monitoring() {
		Collection<MonitoringTarget> monitoringTargets = monitoringTargetService.getMonitoringTargetsForMetricsCollection();
		monitoringTargets.stream()
				.filter(a -> BooleanUtils.isTrue(a.getMetricsCollectionEnabled()))
				.forEach(this::monitoring);
	}

	protected void monitoring(MonitoringTarget monitoringTarget) {
		try {
			monitoringMetricsCollectionService.monitoring(monitoringTarget);
		} catch (Exception e) {
			log.warn("Metrics Monitoring Failed", e);
		}
	}

}
