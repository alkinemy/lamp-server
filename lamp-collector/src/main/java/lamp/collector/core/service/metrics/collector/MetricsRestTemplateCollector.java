package lamp.collector.core.service.metrics.collector;

import lamp.admin.utils.StringUtils;
import lamp.collector.core.domain.TargetMetrics;
import lamp.collector.core.domain.CollectionTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class MetricsRestTemplateCollector implements MetricsCollector {

	private String[] memoryMetricNames = {"mem", "mem.free",
		"heap.committed", "heap.init", "heap.used", "heap",
		"nonheap.committed", "nonheap.init", "nonheap.used", "nonheap"};

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public TargetMetrics getMetrics(CollectionTarget collectionTarget) {
		String url = collectionTarget.getMetricsUrl();
		log.debug("app = {}, url = {}", collectionTarget.getId(), url);
		Map<String, Object> metrics = restTemplate.getForObject(url, LinkedHashMap.class);
		log.debug("metrics = {}", metrics);

		long timestamp = System.currentTimeMillis();
		TargetMetrics targetMetrics = assemble(timestamp, collectionTarget, metrics, collectionTarget.getMetricsPrefix());
		log.debug("targetMetrics = {}", targetMetrics);
		return targetMetrics;
	}


	protected TargetMetrics assemble(long timestamp, CollectionTarget collectionTarget, Map<String, Object> metrics, String prefix) {
		for (String memoryMetricName : memoryMetricNames) {
			metrics.put(memoryMetricName, newMemoryMetric(metrics.get(memoryMetricName)));
		}

		Map<String, Object> assembledMetrics = new LinkedHashMap<>();
		if (StringUtils.isNotBlank(prefix)) {
			metrics.forEach((key, value) -> {
				assembledMetrics.put(prefix + key, value);
			});
		} else {
			assembledMetrics.putAll(metrics);
		}

		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("type", "app");
		tags.put("appId", collectionTarget.getId());
		tags.put("hostname", collectionTarget.getHostname());
		tags.put("address", collectionTarget.getAddress());

		return TargetMetrics.of(timestamp, collectionTarget.getId(), collectionTarget.getName(), assembledMetrics, tags);
	}

	protected Object newMemoryMetric(Object o) {
		if (o instanceof Long) {
			return (Long)o * 1024;
		} else if (o instanceof Integer) {
			return (Integer)o * 1024;
		}
		return o;
	}

}
