package lamp.server.watch.core.service.metrics;

import lamp.server.aladin.utils.NameUtils;
import lamp.server.watch.core.domain.WatchedApp;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpringBootMetricsAssembler implements MetricsAssembler {

	private String[] memoryMetricNames = {"mem", "mem.free",
		"heap.committed", "heap.init", "heap.used", "heap",
		"nonheap.committed", "nonheap.init", "nonheap.used", "nonheap"};

	@Override public Map<String, Object> assemble(WatchedApp watchedApp, Map<String, Object> metrics) {
		for (String memoryMetricName : memoryMetricNames) {
			metrics.put(memoryMetricName, newMemoryMetric(metrics.get(memoryMetricName)));
		}

		Map<String, Object> assembledMetrics = new LinkedHashMap<>();

		for (Map.Entry<String, Object> entry : metrics.entrySet()) {
			String name = NameUtils.name(watchedApp.getId(), entry.getKey());
			assembledMetrics.put(name, entry.getValue());
		}
		return assembledMetrics;
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
