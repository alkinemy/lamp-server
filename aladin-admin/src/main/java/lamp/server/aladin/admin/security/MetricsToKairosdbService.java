package lamp.server.aladin.admin.security;

import lamp.server.aladin.admin.config.KairosdbProperties;
import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.service.MetricsExportService;
import lombok.extern.slf4j.Slf4j;
import org.kairosdb.client.Client;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.MetricBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.util.Map;

@Slf4j
public class MetricsToKairosdbService implements MetricsExportService {

	@Autowired
	private KairosdbProperties kairosdbProperties;

	private Client client;

	@PostConstruct
	public void init() throws MalformedURLException {
		this.client = new HttpClient(kairosdbProperties.getUrl());
	}

	@Override
	@Async
	public void exportMetrics(Agent agent, Map<String, Object> metrics) {
		try {
			log.debug("Export Metrics : {}", kairosdbProperties.getUrl());
			long timestamp = System.currentTimeMillis();
			MetricBuilder metricBuilder = MetricBuilder.getInstance();
			for (Map.Entry<String, Object> entry : metrics.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				metricBuilder.addMetric(key).addDataPoint(timestamp, value)
						.addTag("host", agent.getHostname()).addTag("agent", agent.getId());
			}
			client.pushMetrics(metricBuilder);
		} catch (Exception e) {
			log.warn("Export Metrics failed", e);
		}
	}

}
