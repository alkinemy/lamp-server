package lamp.collector.core.metrics.processor;

import lamp.collector.core.base.event.EventName;
import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.handler.TargetMetricsHandler;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TargetMetricsProcessor<T extends TargetMetrics> {

	private final List<TargetMetricsHandler<T>> targetMetricsHandlers;
	private final EventPublisher eventPublisher;

	public TargetMetricsProcessor(List<TargetMetricsHandler<T>> targetMetricsHandlers) {
		this(targetMetricsHandlers, null);
	}

	public TargetMetricsProcessor(List<TargetMetricsHandler<T>> targetMetricsHandlers, EventPublisher eventPublisher) {
		for (TargetMetricsHandler<T> targetMetricsHandler : targetMetricsHandlers) {
			log.info("targetMetricsHandler registered : {}", targetMetricsHandler);
		}
		log.info("eventPublisher registered {}", eventPublisher);

		this.targetMetricsHandlers = targetMetricsHandlers;
		this.eventPublisher = eventPublisher;
	}

	public void process(T targetMetrics) {
		log.debug("TargetMetrics process : {}", targetMetrics);
		for (TargetMetricsHandler<T> handler : targetMetricsHandlers) {
			doProcess(targetMetrics, handler);
		}
	}

	protected void doProcess(T targetMetrics, TargetMetricsHandler<T> handler) {
		try {
			handler.handle(targetMetrics);
		} catch(Exception e) {
			doProcessException(targetMetrics, "TargetMetrics Process failed", e);
		}
	}

	protected void doProcessException(T targetMetrics, String message, Exception exception) {
		log.warn(message, exception);

		if (eventPublisher != null) {
			Event event = new Event(EventLevel.WARN, EventName.TARGET_METRICS_PROCESS_FAILED, message, exception);
			eventPublisher.publish(event);
		}
	}

}
