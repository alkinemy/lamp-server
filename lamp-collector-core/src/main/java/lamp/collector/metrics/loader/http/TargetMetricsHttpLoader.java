package lamp.collector.metrics.loader.http;

import lamp.collector.metrics.MetricsTarget;
import lamp.collector.metrics.TargetMetrics;
import lamp.collector.metrics.loader.TargetMetricsLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class TargetMetricsHttpLoader extends MetricsHttpLoader implements TargetMetricsLoader {

	@Override
	public TargetMetrics getMetrics(MetricsTarget metricsTarget) {
		long timestamp = System.currentTimeMillis();
		Map<String, Object> metrics = getMetrics(metricsTarget.getEndpoint());

		TargetMetrics targetMetrics = new TargetMetrics(metricsTarget, timestamp, metrics);
		return targetMetrics;
	}

}
