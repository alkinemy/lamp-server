package lamp.collector.core.service.health;

import lamp.collector.core.domain.EventName;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.metrics.HealthTarget;
import lamp.common.metrics.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HealthCollectionService {

	@Autowired
	private EventPublisher eventPublisher;

	@Autowired
	private HealthLoadService healthCollectorService;

	@Autowired
	private HealthExportService healthExportFacadeService;

	@Async
	public void collection(HealthTarget healthTarget) {
		try {
			TargetHealth health = healthCollectorService.getHealth(healthTarget);
			if (health != null) {
				healthExportFacadeService.export(health);
			}
		} catch(Throwable e) {
			log.warn("Health Load failed", e);

			Event event = new Event(EventLevel.WARN, EventName.HEALTH_LOAD_FAILED, e);
			eventPublisher.publish(event);
		}

	}

}
