package lamp.monitoring.core.metrics.service;

import lamp.monitoring.core.metrics.model.MonitoringAlertTarget;

import java.util.List;

@Deprecated
public interface MonitoringMetricsTargetProvider {

	List<MonitoringAlertTarget> getMonitoringMetricsTargets();

}
