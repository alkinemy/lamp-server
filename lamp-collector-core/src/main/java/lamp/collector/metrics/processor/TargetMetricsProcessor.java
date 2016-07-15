package lamp.collector.metrics.processor;


import lamp.collector.metrics.TargetMetrics;

public interface TargetMetricsProcessor<T extends TargetMetrics> {

	void process(T targetMetrics);

}