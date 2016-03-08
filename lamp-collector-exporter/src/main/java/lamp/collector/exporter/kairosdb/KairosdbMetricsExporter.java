package lamp.collector.exporter.kairosdb;

import lamp.collector.common.TargetMetrics;
import lamp.collector.common.metrics.MetricsExporter;
import lombok.extern.slf4j.Slf4j;
import org.kairosdb.client.Client;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.MetricBuilder;

import java.net.MalformedURLException;
import java.util.Map;

@Slf4j
public class KairosdbMetricsExporter implements MetricsExporter {

	private final KairosdbProperties kairosdbProperties;

	private Client client;

	public KairosdbMetricsExporter(KairosdbProperties kairosdbProperties) throws MalformedURLException {
		this.kairosdbProperties = kairosdbProperties;

		log.info("Export KairosDB URL : {}", kairosdbProperties.getUrl());
		this.client = new HttpClient(kairosdbProperties.getUrl());
	}

	@Override
	public void export(TargetMetrics metrics) {
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
