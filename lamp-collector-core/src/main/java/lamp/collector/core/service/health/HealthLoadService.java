package lamp.collector.core.service.health;

import lamp.collector.core.domain.TargetHealthType;
import lamp.common.collection.CollectionTarget;
import lamp.common.metrics.HealthLoader;
import lamp.common.metrics.TargetHealth;
import lamp.metrics.loader.rest.RestTemplateHealthLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class HealthLoadService {

	private Map<String, HealthLoader> loaderMap;

	public HealthLoadService() {
		loaderMap = new HashMap<>();
		loaderMap.put(TargetHealthType.SPRING_BOOT, new RestTemplateHealthLoader());
	}

	public TargetHealth getHealth(CollectionTarget collectionTarget) {
		HealthLoader loader = loaderMap.get(collectionTarget.getHealthType());
		return loader.getHealth(collectionTarget);
	}

}
