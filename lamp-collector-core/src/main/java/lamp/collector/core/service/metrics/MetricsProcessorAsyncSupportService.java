package lamp.collector.core.service.metrics;

import lamp.collector.core.model.EventName;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.collector.service.MetricsProcessor;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class MetricsProcessorAsyncSupportService {

	@Autowired
	private EventPublisher eventPublisher;

	@Async("metricsProcessAsyncExecutor")
	public void process(MetricsProcessor metricsProcessor, MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
		try {
			metricsProcessor.process(metricsTarget, targetMetrics, t);
		} catch(Throwable e) {
			log.warn("Metrics Process failed", e);

			Event event = new Event(EventLevel.WARN, EventName.METRICS_EXPORT_FAILED, e);
			eventPublisher.publish(event);
		}
	}

}
