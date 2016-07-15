package lamp.collector.service.health;

import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;
import lamp.common.collector.service.HealthLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HealthLoaderService {

	private Map<String, HealthLoader> loaderMap;

	public HealthLoaderService() {
		loaderMap = new HashMap<>();
//		loaderMap.put(TargetHealthType.SPRING_BOOT, new SpringBootHealthLoader(new RestTemplate()));
	}

	public TargetHealth getHealth(HealthTarget healthTarget) {
		log.debug("healthType = {}", healthTarget.getHealthType());
		HealthLoader loader = loaderMap.get(healthTarget.getHealthType());
		return loader.getHealth(healthTarget);
	}

}
