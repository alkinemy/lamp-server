package lamp.collector.metrics.loader;


import lamp.collector.metrics.MetricsTarget;
import lamp.collector.metrics.TargetMetrics;

public interface TargetMetricsLoader {

	TargetMetrics getMetrics(MetricsTarget metricsTarget);

}
