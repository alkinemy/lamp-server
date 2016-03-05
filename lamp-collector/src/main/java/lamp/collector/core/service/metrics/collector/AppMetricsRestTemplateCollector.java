package lamp.collector.core.service.metrics.collector;

import lamp.collector.core.domain.AppMetrics;
import lamp.collector.core.domain.TargetApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class AppMetricsRestTemplateCollector implements AppMetricsCollector {

	private String[] memoryMetricNames = {"mem", "mem.free",
		"heap.committed", "heap.init", "heap.used", "heap",
		"nonheap.committed", "nonheap.init", "nonheap.used", "nonheap"};

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public AppMetrics getMetrics(TargetApp targetApp) {
		String url = targetApp.getMetricsUrl();
		log.debug("app = {}, url = {}", targetApp.getId(), url);
		Map<String, Object> metrics = restTemplate.getForObject(url, LinkedHashMap.class);

		long timestamp = System.currentTimeMillis();
		AppMetrics watchedAppMetrics = assemble(timestamp, targetApp, metrics);
		log.debug("watchedAppMetrics = {}", watchedAppMetrics);
		return watchedAppMetrics;
	}


	protected AppMetrics assemble(long timestamp, TargetApp targetApp, Map<String, Object> metrics) {
		for (String memoryMetricName : memoryMetricNames) {
			metrics.put(memoryMetricName, newMemoryMetric(metrics.get(memoryMetricName)));
		}

		Map<String, Object> assembledMetrics = new LinkedHashMap<>();

		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("type", "app");
		tags.put("appId", targetApp.getId());
		tags.put("hostname", targetApp.getHostname());
		tags.put("address", targetApp.getAddress());

		return AppMetrics.of(timestamp, targetApp.getId(), targetApp.getName(), assembledMetrics, tags);
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
