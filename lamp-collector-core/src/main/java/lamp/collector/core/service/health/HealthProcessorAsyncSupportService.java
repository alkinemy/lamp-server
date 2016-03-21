package lamp.collector.core.service.health;

import lamp.collector.core.model.EventName;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.collector.service.HealthProcessor;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class HealthProcessorAsyncSupportService {

	@Autowired
	private EventPublisher eventPublisher;

	@Async("healthProcessAsyncExecutor")
	public void process(HealthProcessor healthProcessor, HealthTarget healthTarget, TargetHealth targetHealth, Throwable t) {
		try {
			healthProcessor.process(healthTarget, targetHealth, t);
		} catch(Throwable e) {
			log.warn("Health Process failed", e);

			Event event = new Event(EventLevel.WARN, EventName.HEALTH_PROCESS_FAILED, e);
			eventPublisher.publish(event);
		}

	}
}
