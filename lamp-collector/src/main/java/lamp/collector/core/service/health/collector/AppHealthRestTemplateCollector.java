package lamp.collector.core.service.health.collector;

import lamp.collector.core.domain.AppHealth;
import lamp.collector.core.domain.TargetApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AppHealthRestTemplateCollector implements AppHealthCollector {


	private static final String STATUS = "status";
	private static final String DESCRIPTION = "description";

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public AppHealth getHealth(TargetApp targetApp) {
		long timestamp = System.currentTimeMillis();

		String url = targetApp.getHealthUrl();
		log.debug("app = {}, url = {}", targetApp.getId(), url);

		Map<String, Object> healthMap = getHealthMap(url);
		log.debug("healthMap = {}", healthMap);

		Map<String, String> tags = new LinkedHashMap<>();

		AppHealth health = AppHealth.of(timestamp, targetApp.getId(), targetApp.getName(), healthMap, tags);
		log.debug("health = {}", health);
		return health;
	}


	protected Map<String, Object> getHealthMap(String url) {
		Map<String, Object> health;
		try {
			health = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			log.warn("get health failed", e);
			health = new HashMap<>();
			health.put(STATUS, "OUT_OF_SERVICE");
			health.put(DESCRIPTION, e.getMessage());
		}

		return health;
	}

}
