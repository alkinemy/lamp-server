package lamp.server.aladin.admin.service.metrics;

import lamp.server.aladin.admin.config.metrics.KairosdbProperties;
import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.domain.AgentMetrics;
import lamp.server.aladin.core.service.MetricsExportService;
import lombok.extern.slf4j.Slf4j;
import org.kairosdb.client.Client;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.MetricBuilder;
import org.springframework.scheduling.annotation.Async;

import java.net.MalformedURLException;
import java.util.Map;

@Slf4j
public class MetricsExportKairosdbService implements MetricsExportService {

	private final KairosdbProperties kairosdbProperties;

	private Client client;

	public MetricsExportKairosdbService(KairosdbProperties kairosdbProperties) throws MalformedURLException {
		this.kairosdbProperties = kairosdbProperties;

		this.client = new HttpClient(kairosdbProperties.getUrl());
	}

	@Override
	@Async
	public void exportMetrics(AgentMetrics agentMetrics) {
		try {
			log.debug("Export Metrics : {}", kairosdbProperties.getUrl());
			long timestamp = agentMetrics.getTimestamp();
			Map<String, Object> metrics = agentMetrics.getMetrics();
			Map<String, String> tags = agentMetrics.getTags();
			MetricBuilder metricBuilder = MetricBuilder.getInstance();
			for (Map.Entry<String, Object> entry : metrics.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				metricBuilder.addMetric(key).addDataPoint(timestamp, value).addTags(tags);
			}
			client.pushMetrics(metricBuilder);
		} catch (Exception e) {
			log.warn("Export Metrics failed", e);
		}
	}

}
