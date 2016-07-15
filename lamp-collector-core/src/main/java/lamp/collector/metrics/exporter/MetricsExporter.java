package lamp.collector.metrics.exporter;

import lamp.common.collector.service.MetricsProcessor;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated
public abstract class MetricsExporter implements MetricsProcessor {

	@Override
	public boolean canProcess(MetricsTarget metricsTarget) {
		return true;
	}

	@Override
	public void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
		if (targetMetrics != null) {
			export(targetMetrics);
		} else {
			log.warn("targetMetrics is null : target={}, throwable={}", metricsTarget, t);
		}
	}

	public abstract void export(TargetMetrics targetMetrics);
}