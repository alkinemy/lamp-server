package lamp.collector.app.core.service.health;

import lamp.collector.app.core.domain.EventName;
import lamp.collector.app.core.domain.TargetHealthType;
import lamp.collector.common.CollectionTarget;
import lamp.collector.common.TargetHealth;
import lamp.collector.common.health.HealthLoader;
import lamp.collector.loader.rest.RestTemplateHealthLoader;
import lamp.event.common.Event;
import lamp.event.common.EventLevel;
import lamp.event.common.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
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
