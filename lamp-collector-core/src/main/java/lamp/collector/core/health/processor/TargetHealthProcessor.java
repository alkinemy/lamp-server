package lamp.collector.core.health.processor;

import lamp.collector.core.base.event.EventName;
import lamp.collector.core.health.TargetHealth;
import lamp.collector.core.health.handler.TargetHealthHandler;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TargetHealthProcessor<T extends TargetHealth> {

	private final List<TargetHealthHandler<T>> targetHealthHandlers;
	private final EventPublisher eventPublisher;

	public TargetHealthProcessor(List<TargetHealthHandler<T>> targetHealthHandlers) {
		this(targetHealthHandlers, null);
	}

	public TargetHealthProcessor(List<TargetHealthHandler<T>> targetHealthHandlers, EventPublisher eventPublisher) {
		this.targetHealthHandlers = targetHealthHandlers;
		this.eventPublisher = eventPublisher;
	}

	public void process(T targetHealth) {
		for (TargetHealthHandler<T> handler : targetHealthHandlers) {
			doProcess(targetHealth, handler);
		}
	}

	protected void doProcess(T targetHealth, TargetHealthHandler<T> handler) {
		try {
			handler.handle(targetHealth);
		} catch(Exception e) {
			doProcessException(targetHealth, "TargetHealth Process failed", e);
		}
	}

	protected void doProcessException(T targetHealth, String message, Exception exception) {
		log.warn(message, exception);

		if (eventPublisher != null) {
			Event event = new Event(EventLevel.WARN, EventName.TARGET_HEALTH_PROCESS_FAILED, message, exception);
			eventPublisher.publish(event);
		}
	}

}
