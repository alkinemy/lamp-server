package lamp.collector.common.metrics;

import lamp.collector.common.TargetMetrics;

public interface MetricsExporter {

	void export(TargetMetrics targetMetrics);

}