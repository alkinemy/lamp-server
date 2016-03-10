package lamp.common.metrics;

public interface MetricsExporter {

	void export(TargetMetrics targetMetrics);

}