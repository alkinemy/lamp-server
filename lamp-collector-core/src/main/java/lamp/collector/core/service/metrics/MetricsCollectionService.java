package lamp.collector.core.service.metrics;

import lamp.collector.core.domain.EventName;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.metrics.MetricsTarget;
import lamp.common.metrics.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetricsCollectionService {

	@Autowired
	private EventPublisher eventPublisher;

	@Autowired
	private MetricsLoadService metricsCollectorService;

	@Autowired
	private MetricsExportService metricsExportService;

	@Async
	public void collection(MetricsTarget metricsTarget) {
		try {
			TargetMetrics metrics = metricsCollectorService.getMetrics(metricsTarget);
			if (metrics != null) {
				metricsExportService.export(metrics);
			}
		} catch(Throwable e) {
			log.warn("Metrics Load failed", e);

			Event event = new Event(EventLevel.WARN, EventName.METRICS_LOAD_FAILED, e);
			eventPublisher.publish(event);
		}

	}

}
