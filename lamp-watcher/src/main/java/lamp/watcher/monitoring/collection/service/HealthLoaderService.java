package lamp.watcher.monitoring.collection.service;

import lamp.collector.loader.rest.RestTemplateHealthLoader;
import lamp.common.collection.CollectionTarget;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.collection.health.HealthLoader;
import lamp.common.collection.health.TargetHealth;
import lamp.watcher.core.domain.EventName;
import lamp.watcher.core.domain.TargetHealthType;
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
		// TODO 수정해라
		loaderMap = new HashMap<>();
		loaderMap.put(TargetHealthType.SPRING_BOOT, new RestTemplateHealthLoader());
	}

	public TargetHealth getHealth(CollectionTarget collectionTarget) {
		try {
			HealthLoader loader = loaderMap.get(collectionTarget.getHealthType());
			return loader.getHealth(collectionTarget);
		} catch(Throwable e) {
			log.warn("Health Load failed", e);

			Event event = new Event(EventLevel.WARN, EventName.HEALTH_LOAD_FAILED, e);
			eventPublisher.publish(event);
		}
		return null;
	}

}
