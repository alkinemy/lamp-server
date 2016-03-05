package lamp.collector.core.service.metrics.collector;

import lamp.collector.core.domain.AppMetrics;
import lamp.collector.core.domain.WatchedApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class AppMetricsSpringBootCollector implements AppMetricsCollector {

	private String[] memoryMetricNames = {"mem", "mem.free",
		"heap.committed", "heap.init", "heap.used", "heap",
		"nonheap.committed", "nonheap.init", "nonheap.used", "nonheap"};

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public AppMetrics getMetrics(WatchedApp watchedApp) {
		String url = watchedApp.getMetricsUrl();
		log.debug("app = {}, url = {}", watchedApp.getId(), url);
		Map<String, Object> metrics = restTemplate.getForObject(url, LinkedHashMap.class);

		long timestamp = System.currentTimeMillis();
		AppMetrics watchedAppMetrics = assemble(timestamp, watchedApp, metrics);
		log.debug("watchedAppMetrics = {}", watchedAppMetrics);
		return watchedAppMetrics;
	}


	protected AppMetrics assemble(long timestamp, WatchedApp watchedApp, Map<String, Object> metrics) {
		for (String memoryMetricName : memoryMetricNames) {
			metrics.put(memoryMetricName, newMemoryMetric(metrics.get(memoryMetricName)));
		}

		Map<String, Object> assembledMetrics = new LinkedHashMap<>();

		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("type", "app");
		tags.put("appId", watchedApp.getId());
		tags.put("hostname", watchedApp.getHostname());
		tags.put("address", watchedApp.getAddress());

		return AppMetrics.of(timestamp, watchedApp.getId(), watchedApp.getName(), assembledMetrics, tags);
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
