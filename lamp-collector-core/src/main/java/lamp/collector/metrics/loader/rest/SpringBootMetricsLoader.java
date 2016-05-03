package lamp.collector.metrics.loader.rest;

import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
public class SpringBootMetricsLoader extends RestTemplateMetricsLoader {

	private String[] memoryMetricNames = {"mem", "mem.free",
		"heap.committed", "heap.init", "heap.used", "heap",
		"nonheap.committed", "nonheap.init", "nonheap.used", "nonheap"};

	public SpringBootMetricsLoader(RestTemplate restTemplate) {
		super(restTemplate);
	}

	@Override
	protected TargetMetrics assemble(long timestamp, MetricsTarget metricsTarget, Map<String, Object> metrics) {
		for (String memoryMetricName : memoryMetricNames) {
			Object memValue = metrics.get(memoryMetricName);
			if (memValue != null) {
				metrics.put(memoryMetricName, newMemoryMetric(memValue));
			}
		}
		return super.assemble(timestamp, metricsTarget, metrics);
	}

	/**
	 * KB -> B
	 * @param o
	 * @return
	 */
	protected Object newMemoryMetric(Object o) {
		if (o instanceof Long) {
			return (Long)o * 1024;
		} else if (o instanceof Integer) {
			return (Integer)o * 1024;
		}
		return o;
	}

}
