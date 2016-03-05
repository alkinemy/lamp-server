package lamp.collector.core.service.health.collector;

import lamp.collector.core.domain.TargetHealth;
import lamp.collector.core.domain.TargetHealthType;
import lamp.collector.core.domain.CollectionTarget;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HealthCollectorService {

	private Map<String, HealthCollector> collectorMap;

	public HealthCollectorService() {
		// TODO 수정해라
		collectorMap = new HashMap<>();
		collectorMap.put(TargetHealthType.SPRING_BOOT, new HealthRestTemplateCollector());
	}

	public TargetHealth getHealth(CollectionTarget collectionTarget) {
		HealthCollector collector = collectorMap.get(collectionTarget.getHealthType());
		return collector.getHealth(collectionTarget);
	}

}
