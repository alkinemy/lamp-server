package lamp.collector.core.metrics.loader;


import lamp.collector.core.metrics.MetricsTarget;
import lamp.collector.core.metrics.TargetMetrics;

public interface TargetMetricsLoader<T extends MetricsTarget,  M extends TargetMetrics>  {

	M getMetrics(T target);

}
