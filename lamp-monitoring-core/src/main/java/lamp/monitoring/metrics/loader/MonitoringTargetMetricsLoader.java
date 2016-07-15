package lamp.monitoring.metrics.loader;


import lamp.collector.core.metrics.loader.TargetMetricsLoader;
import lamp.monitoring.metrics.MonitoringMetricsTarget;
import lamp.monitoring.metrics.MonitoringTargetMetrics;

public interface MonitoringTargetMetricsLoader extends TargetMetricsLoader<MonitoringMetricsTarget, MonitoringTargetMetrics> {

	MonitoringTargetMetrics getMetrics(MonitoringMetricsTarget metricsTarget);

}
