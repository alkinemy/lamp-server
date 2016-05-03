package lamp.common.collector.service;

import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;

import java.util.List;

public interface TargetMetricsListLoader extends MetricsLoader {

	List<TargetMetrics> getMetricsList(MetricsTarget metricsTarget);

}
