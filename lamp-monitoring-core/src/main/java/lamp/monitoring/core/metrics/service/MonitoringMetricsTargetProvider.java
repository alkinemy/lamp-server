package lamp.monitoring.core.metrics.service;

import lamp.monitoring.core.metrics.model.MonitoringMetricsTarget;

import java.util.List;

public interface MonitoringMetricsTargetProvider {

	List<MonitoringMetricsTarget> getMonitoringMetricsTargets();

}
