package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AgentMetricCollectService {

	private RestTemplate restTemplate = new RestTemplate();

	private String[] memoryMetricNames = {"mem", "mem.free",
			"heap.committed", "heap.init", "heap.used", "heap",
			"nonheap.committed", "nonheap.init", "nonheap.used", "nonheap"};
	private String serverMetricsPrefix = "server.";


	@Autowired(required = false)
	private List<MetricsExportService> metricsExportServices;

	@Async
	public void collectMetrics(Agent agent) {
		String url = agent.getMetricsUrl();
		log.debug("agent = {}, url = {}", agent.getId(), url);
		if (StringUtils.isNotBlank(url)) {
			Map<String, Object> metrics = restTemplate.getForObject(url, LinkedHashMap.class);

			for (String memoryMetricName : memoryMetricNames) {
				metrics.put(memoryMetricName, newMemoryMetric(metrics.get(memoryMetricName)));
			}

			Map<String, Object> metricsWithName = new LinkedHashMap<>();

			for (Map.Entry<String, Object> entry : metrics.entrySet()) {
				String name = entry.getKey();
				if (!name.startsWith(serverMetricsPrefix)) {
					name = "lamp.agent." + name;
				}
				metricsWithName.put(name, entry.getValue());
			}

			log.debug("metrics = {}", metricsWithName);
			if (metricsExportServices != null) {
				for (MetricsExportService metricsExportService : metricsExportServices) {
					try {
						metricsExportService.exportMetrics(agent, metricsWithName);
					} catch(Throwable e) {
						log.warn("Export Metrics failed", e);
					}
				}
			}
		}
	}

	private Object newMemoryMetric(Object o) {
		if (o instanceof Long) {
			return (Long)o * 1024;
		} else if (o instanceof Integer) {
			return (Integer)o * 1024;
		}
		return o;
	}

}
