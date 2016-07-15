package lamp.common.collector.service;

import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;

@Deprecated
public interface MetricsProcessor {

	boolean canProcess(MetricsTarget metricsTarget);

	void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t);

}