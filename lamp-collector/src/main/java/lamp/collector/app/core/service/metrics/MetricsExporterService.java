package lamp.collector.app.core.service.metrics;

import lamp.collector.app.core.domain.EventName;
import lamp.common.collection.metrics.MetricsExporter;
import lamp.common.collection.metrics.TargetMetrics;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MetricsExporterService {

	@Autowired
	private EventPublisher eventPublisher;

	@Autowired(required = false)
	private List<MetricsExporter> metricsExporters;

	public void export(TargetMetrics targetMetrics) {
		if (metricsExporters != null) {
			for (MetricsExporter metricsExporter : metricsExporters) {
				try {
					metricsExporter.export(targetMetrics);
				} catch(Throwable e) {
					log.warn("Metrics Export failed", e);

					Event event = new Event(EventLevel.WARN, EventName.METRICS_EXPORT_FAILED, e);
					eventPublisher.publish(event);
				}
			}
		}
	}

}
