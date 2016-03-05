package lamp.collector.core.service.metrics.export;

import lamp.collector.core.config.base.KairosdbProperties;
import lamp.collector.core.domain.TargetMetrics;
import lamp.collector.core.domain.CollectionTarget;
import lombok.extern.slf4j.Slf4j;
import org.kairosdb.client.Client;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.MetricBuilder;

import java.net.MalformedURLException;
import java.util.Map;

@Slf4j
public class MetricsExportKairosdbService implements MetricsExportService {

	private final KairosdbProperties kairosdbProperties;

	private Client client;

	public MetricsExportKairosdbService(KairosdbProperties kairosdbProperties) throws MalformedURLException {
		this.kairosdbProperties = kairosdbProperties;

		log.info("Export KairosDB URL : {}", kairosdbProperties.getUrl());
		this.client = new HttpClient(kairosdbProperties.getUrl());
	}

	@Override public void export(CollectionTarget collectionTarget, TargetMetrics metrics) {
		try {
			long timestamp = metrics.getTimestamp();
			MetricBuilder metricBuilder = MetricBuilder.getInstance();
			for (Map.Entry<String, Object> entry : metrics.getMetrics().entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				metricBuilder.addMetric(key).addDataPoint(timestamp, value).addTags(metrics.getTags());
			}
			client.pushMetrics(metricBuilder);
		} catch (Exception e) {
			log.warn("Metrics Export To KairosDB failed", e);
		}
	}

}
