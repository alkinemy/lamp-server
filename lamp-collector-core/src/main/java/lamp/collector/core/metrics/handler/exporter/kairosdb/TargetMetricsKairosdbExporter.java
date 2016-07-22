package lamp.collector.core.metrics.handler.exporter.kairosdb;

import lamp.collector.core.base.event.EventName;
import lamp.collector.core.base.exception.TargetMetricsExportException;
import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.handler.exporter.AbstractTargetMetricsExporter;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.utils.CollectionUtils;
import lamp.common.utils.MapUtils;
import lamp.support.kairosdb.KairosdbProperties;
import lombok.extern.slf4j.Slf4j;
import org.kairosdb.client.Client;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.MetricBuilder;
import org.kairosdb.client.response.Response;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@Slf4j
public class TargetMetricsKairosdbExporter extends AbstractTargetMetricsExporter {

	private final KairosdbProperties kairosdbProperties;

	private Client client;

	public TargetMetricsKairosdbExporter(KairosdbProperties kairosdbProperties, EventPublisher eventPublisher) throws MalformedURLException {
		super(eventPublisher);
		
		this.kairosdbProperties = kairosdbProperties;

		log.info("KairosdbProperties : {}", kairosdbProperties);
		this.client = new HttpClient(kairosdbProperties.getUrl());
	}

	@Override
	public void doHandle(TargetMetrics targetMetrics) {
		log.debug("TargetMetrics doHandle : {}", targetMetrics);
		Map<String, Object> metrics = targetMetrics.getMetrics();
		if (MapUtils.isEmpty(metrics)) {
			return;
		}

		Map<String, String> tags = targetMetrics.getTags();
		long timestamp = targetMetrics.getTimestamp();

		try {
			MetricBuilder metricBuilder = MetricBuilder.getInstance();
			for (Map.Entry<String, Object> entry : metrics.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					metricBuilder.addMetric(key).addDataPoint(timestamp, value).addTags(tags);
				}
			}
			Response response = client.pushMetrics(metricBuilder);
			handleResponse(targetMetrics, response);
		} catch (Exception e) {
			handleException(targetMetrics, "TargetMetrics Kairosdb Export Failed", e);
		}
	}

	protected void handleResponse(TargetMetrics targetMetrics, Response response) {
		if (response.getStatusCode() / 100 != 2) {
			List<String> errors = response.getErrors();
			if (CollectionUtils.isNotEmpty(errors)) {
				for (int i = 0; i < errors.size(); i++) {
					log.warn("TargetMetrics Kairosdb Export errors[{}] : {}", i, errors.get(i));
				}
				handleException(targetMetrics, "TargetMetrics Kairosdb Export Failed", new TargetMetricsExportException(errors.get(0)));
			} else {
				String msg = "StatusCode is " + response.getStatusCode();
				handleException(targetMetrics, "TargetMetrics Kairosdb Export Failed", new TargetMetricsExportException(msg));
			}

		}
	}

}
