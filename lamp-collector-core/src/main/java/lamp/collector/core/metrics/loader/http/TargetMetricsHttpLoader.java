package lamp.collector.core.metrics.loader.http;

import lamp.collector.core.metrics.MetricsTarget;
import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.loader.TargetMetricsLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class TargetMetricsHttpLoader extends MetricsHttpLoader implements TargetMetricsLoader {

	@Override
	public TargetMetrics getMetrics(MetricsTarget metricsTarget) {
		long timestamp = System.currentTimeMillis();
		try {
			Map<String, Object> metrics = getMetrics(metricsTarget.getEndpoint());

			return new TargetMetrics(metricsTarget, timestamp, metrics);
		} catch (Exception e) {
			return new TargetMetrics(metricsTarget, timestamp, e);
		}
	}

}
