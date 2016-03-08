package lamp.collector.loader.rest;

import lamp.common.collection.CollectionTarget;
import lamp.common.collection.metrics.MetricsLoader;
import lamp.common.collection.metrics.TargetMetrics;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class RestTemplateMetricsLoader implements MetricsLoader {

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
		TargetMetrics targetMetrics = assemble(timestamp, collectionTarget, metrics, collectionTarget.getMetricsExportPrefix());
		log.debug("targetMetrics = {}", targetMetrics);
		return targetMetrics;
	}


	protected TargetMetrics assemble(long timestamp, CollectionTarget collectionTarget, Map<String, Object> metrics, String prefix) {
		for (String memoryMetricName : memoryMetricNames) {
			metrics.put(memoryMetricName, newMemoryMetric(metrics.get(memoryMetricName)));
		}

		Map<String, Object> assembledMetrics;
		if (StringUtils.isNotBlank(prefix)) {
			assembledMetrics = metrics.entrySet()
					.stream()
					.collect(Collectors.toMap(k -> prefix + k, Map.Entry::getValue));
		} else {
			assembledMetrics = metrics.entrySet()
					.stream()
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}

		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("type", "app");
		tags.put("id", collectionTarget.getId());
		tags.put("hostname", collectionTarget.getHostname());
		tags.put("address", collectionTarget.getAddress());

		return new TargetMetrics(timestamp, collectionTarget.getId(), collectionTarget.getName(), assembledMetrics, tags);
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
