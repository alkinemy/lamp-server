package lamp.admin.core.monitoring.service;

import lamp.admin.core.agent.domain.Agent;
import lamp.admin.core.agent.domain.TargetServer;
import lamp.admin.core.agent.service.AgentService;
import lamp.admin.core.monitoring.domain.TargetServerMetrics;
import lamp.admin.core.support.agent.AgentClient;
import lamp.admin.utils.NameUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AgentMetricCollectService {


	private String[] memoryMetricNames = {"mem", "mem.free",
			"heap.committed", "heap.init", "heap.used", "heap",
			"nonheap.committed", "nonheap.init", "nonheap.used", "nonheap"};
	private String serverMetricsPrefix = "server.";
	private String lampAgentMetricsPrefix = "lamp.agent.";

	@Autowired
	private AgentClient agentClient;

	@Autowired
	private AgentService agentService;

	@Autowired(required = false)
	private List<MetricsExportService> metricsExportServices;

	@Async
	public void collectMetrics(TargetServer targetServer) {
		log.debug("collectMetrics : TargetServer={}", targetServer.getId());
		if (targetServer.getAgentMetricsCollectEnabled()) {
			Agent agent = agentService.getAgentByTargetServerId(targetServer.getId());

			String url = agent.getMetricsUrl();
			log.debug("metricsUrl = {}", url);
			Map<String, Object> metrics = agentClient.getForObject(agent, url, LinkedHashMap.class);

			for (String memoryMetricName : memoryMetricNames) {
				metrics.put(memoryMetricName, newMemoryMetric(metrics.get(memoryMetricName)));
			}

			Map<String, Object> metricsWithName = new LinkedHashMap<>();

			for (Map.Entry<String, Object> entry : metrics.entrySet()) {
				String name = entry.getKey();
				if (!name.startsWith(serverMetricsPrefix)) {
					name = NameUtils.name(lampAgentMetricsPrefix, name);
				}
				metricsWithName.put(name, entry.getValue());
			}

			log.debug("metrics = {}", metricsWithName);
			if (metricsExportServices != null) {
				long timestamp = System.currentTimeMillis();
				for (MetricsExportService metricsExportService : metricsExportServices) {
					try {
						Map<String ,String> tags = new LinkedHashMap<>();
						tags.put("host", agent.getHostname());
						tags.put("agent", agent.getId());

						TargetServerMetrics targetMetrics = TargetServerMetrics.of(timestamp, targetServer.getHostname(), targetServer.getName(), metrics, tags);
						metricsExportService.exportMetrics(targetMetrics);
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
