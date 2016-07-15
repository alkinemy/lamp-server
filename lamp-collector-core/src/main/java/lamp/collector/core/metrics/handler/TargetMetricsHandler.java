package lamp.collector.core.metrics.handler;


import lamp.collector.core.metrics.TargetMetrics;

public interface TargetMetricsHandler<T extends TargetMetrics> {

	void handle(T targetMetrics);

}