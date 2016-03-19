package lamp.watcher.core.service;

import lamp.common.collector.TargetHealth;
import lamp.common.collector.TargetMetrics;
import lamp.common.monitoring.MonitoringTarget;

import java.util.List;
import java.util.Optional;

public interface MonitoringTargetService {

	List<MonitoringTarget> getMonitoringTargetsForHealthCollection();

	List<MonitoringTarget> getMonitoringTargetsForMetricsCollection();

	Optional<MonitoringTarget> getMonitoringTargetOptional(String id);


	MonitoringTarget createMonitoringTarget(TargetHealth targetHealth);

	MonitoringTarget createMonitoringTarget(TargetMetrics targetMetrics);
}
