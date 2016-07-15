package lamp.collector.metrics.exporter.kairosdb;

import lamp.collector.core.model.EventName;
import lamp.collector.metrics.exporter.MetricsExporter;
import lamp.common.collector.model.TargetMetrics;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.support.kairosdb.KairosdbProperties;
import lombok.extern.slf4j.Slf4j;
import org.kairosdb.client.Client;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.MetricBuilder;

import java.net.MalformedURLException;
import java.util.Map;

@Slf4j
@Deprecated
public class KairosdbMetricsExporter extends MetricsExporter {

	private EventPublisher eventPublisher;

	private final KairosdbProperties kairosdbProperties;

	private Client client;

	public KairosdbMetricsExporter(EventPublisher eventPublisher, KairosdbProperties kairosdbProperties) throws MalformedURLException {
		this.eventPublisher = eventPublisher;

		this.kairosdbProperties = kairosdbProperties;

		log.info("Export KairosDB URL : {}", kairosdbProperties.getUrl());
		this.client = new HttpClient(kairosdbProperties.getUrl());
	}

	@Override
	public void export(TargetMetrics metrics) {
		log.debug("Kairosdb Metrics Export : {}", metrics);

		try {
			long timestamp = metrics.getTimestamp();
			MetricBuilder metricBuilder = MetricBuilder.getInstance();
			for (Map.Entry<String, Object> entry : metrics.getMetrics().entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					metricBuilder.addMetric(key).addDataPoint(timestamp, value).addTags(metrics.getTags());
				}
			}
			client.pushMetrics(metricBuilder);
		} catch (Exception e) {
			log.warn("Kairosdb Metrics Export Failed", e);
			eventPublisher.publish(new Event(EventLevel.WARN, EventName.METRICS_EXPORT_TO_KAIROSDB_FAILED, e, metrics));
		}
	}

}
