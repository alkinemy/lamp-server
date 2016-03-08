package lamp.collector.loader.rest;

import lamp.common.collection.CollectionTarget;
import lamp.common.collection.health.HealthLoader;
import lamp.common.collection.health.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class RestTemplateHealthLoader implements HealthLoader {

	private static final String STATUS = "status";
	private static final String DESCRIPTION = "description";

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public TargetHealth getHealth(CollectionTarget collectionTarget) {
		long timestamp = System.currentTimeMillis();

		String url = collectionTarget.getHealthUrl();
		log.debug("app = {}, url = {}", collectionTarget.getId(), url);

		Map<String, Object> healthMap = getHealthMap(url);
		log.debug("healthMap = {}", healthMap);

		Map<String, String> tags = new LinkedHashMap<>();

		TargetHealth health = new TargetHealth(timestamp, collectionTarget.getId(), collectionTarget.getName(), healthMap, tags);
		log.debug("health = {}", health);
		return health;
	}


	protected Map<String, Object> getHealthMap(String url) {
//		Map<String, Object> health;
//		try {
//			health =
//		} catch (Exception e) {
//			log.warn("get health failed", e);
//			health = new HashMap<>();
//			health.put(STATUS, "OUT_OF_SERVICE");
//			health.put(DESCRIPTION, e.getMessage());
//		}

		return restTemplate.getForObject(url, Map.class);
	}

}
