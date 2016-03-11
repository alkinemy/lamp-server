package lamp.watcher.monitoring.collection.service;

import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.metrics.HealthLoader;
import lamp.common.metrics.HealthTarget;
import lamp.common.metrics.TargetHealth;
import lamp.watcher.core.domain.EventName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HealthLoaderService {

	@Autowired
	private EventPublisher eventPublisher;

	private Map<String, HealthLoader> loaderMap;

	public HealthLoaderService() {
		loaderMap = new HashMap<>();
//		loaderMap.put(TargetHealthType.SPRING_BOOT, new RestTemplateHealthLoader());
	}

	public TargetHealth getHealth(HealthTarget healthTarget) {
		try {
			HealthLoader loader = loaderMap.get(healthTarget.getHealthType());
			return loader.getHealth(healthTarget);
		} catch(Throwable e) {
			log.warn("Health Load failed", e);

			Event event = new Event(EventLevel.WARN, EventName.HEALTH_LOAD_FAILED, e);
			eventPublisher.publish(event);
		}
		return null;
	}

}
