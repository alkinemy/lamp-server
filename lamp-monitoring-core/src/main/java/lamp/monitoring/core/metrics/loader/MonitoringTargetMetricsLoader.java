package lamp.monitoring.core.metrics.loader;


import lamp.collector.core.metrics.loader.TargetMetricsLoader;
import lamp.monitoring.core.metrics.MonitoringMetricsTarget;
import lamp.monitoring.core.metrics.MonitoringTargetMetrics;

public interface MonitoringTargetMetricsLoader extends TargetMetricsLoader<MonitoringMetricsTarget, MonitoringTargetMetrics> {

	MonitoringTargetMetrics getMetrics(MonitoringMetricsTarget metricsTarget);

}
