package lamp.monitoring.metrics.loader;


import lamp.monitoring.metrics.MonitoringMetricsTarget;
import lamp.monitoring.metrics.MonitoringTargetMetrics;

public interface MonitoringTargetMetricsLoader {

	MonitoringTargetMetrics getMetrics(MonitoringMetricsTarget metricsTarget);

}
