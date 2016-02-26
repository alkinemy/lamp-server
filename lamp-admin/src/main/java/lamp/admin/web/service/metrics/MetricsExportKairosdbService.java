package lamp.admin.web.service.metrics;

import lamp.admin.core.monitoring.domain.TargetServerMetrics;
import lamp.admin.core.monitoring.service.MetricsExportService;
import lamp.admin.web.config.metrics.KairosdbProperties;
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
	public void exportMetrics(TargetServerMetrics agentMetrics) {
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
