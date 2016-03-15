package lamp.metrics.exporter;

import lamp.common.metrics.MetricsProcessor;
import lamp.common.metrics.MetricsTarget;
import lamp.common.metrics.TargetMetrics;

public abstract class MetricsExporter implements MetricsProcessor {

	@Override
	public void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
		if (targetMetrics != null) {
			export(targetMetrics);
		}
	}

	protected abstract void export(TargetMetrics targetMetrics);
}