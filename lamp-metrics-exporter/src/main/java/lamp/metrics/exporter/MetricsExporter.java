package lamp.metrics.exporter;

import lamp.common.collector.service.MetricsProcessor;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;

public abstract class MetricsExporter implements MetricsProcessor {

	@Override
	public boolean canProcess(MetricsTarget metricsTarget) {
		return true;
	}

	@Override
	public void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
		if (targetMetrics != null) {
			export(targetMetrics);
		}
	}

	protected abstract void export(TargetMetrics targetMetrics);
}