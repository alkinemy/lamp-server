package lamp.collector.core.service.health;

import lamp.collector.core.domain.TargetHealthType;
import lamp.common.metrics.HealthLoader;
import lamp.common.metrics.HealthTarget;
import lamp.common.metrics.TargetHealth;
import lamp.metrics.loader.rest.SpringBootHealthLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class HealthLoadService {

	private Map<String, HealthLoader> loaderMap;

	public HealthLoadService() {
		loaderMap = new HashMap<>();
		loaderMap.put(TargetHealthType.SPRING_BOOT, new SpringBootHealthLoader(new RestTemplate()));
	}

	public TargetHealth getHealth(HealthTarget healthTarget) {
		log.debug("healthType = {}", healthTarget.getHealthType());
		HealthLoader loader = loaderMap.get(healthTarget.getHealthType());
		return loader.getHealth(healthTarget);
	}

}
