package lamp.common.metrics;

public interface MetricsProcessor {

	void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t);

}