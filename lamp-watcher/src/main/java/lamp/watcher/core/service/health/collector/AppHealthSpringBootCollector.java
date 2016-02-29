package lamp.watcher.core.service.health.collector;

import lamp.admin.utils.NameUtils;
import lamp.watcher.core.domain.AppHealth;
import lamp.watcher.core.domain.AppHealthStatus;
import lamp.watcher.core.domain.AppMetrics;
import lamp.watcher.core.domain.WatchedApp;
import lamp.watcher.core.service.metrics.collector.AppMetricsCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AppHealthSpringBootCollector implements AppHealthCollector {


	private static final String STATUS = "status";
	private static final String DESCRIPTION = "description";

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public AppHealth getHealth(WatchedApp watchedApp) {
		String url = watchedApp.getHealthUrl();
		log.debug("app = {}, url = {}", watchedApp.getId(), url);

		Map<String, Object> healthMap = getHealthMap(url);
		log.debug("healthMap = {}", healthMap);

		String status = (String) healthMap.get(STATUS);
		String description = (String) healthMap.get(DESCRIPTION);

		Map<String, Object> details = healthMap.entrySet().stream()
				.filter(e -> !STATUS.equals(e.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		AppHealth health = AppHealth.of(watchedApp.getId(), watchedApp.getName(), status, description, details);
		log.debug("health = {}", health);
		return health;
	}


	protected Map<String, Object> getHealthMap(String url) {
		Map<String, Object> health;
		try {
			health = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			log.warn("health failed", e);
			health = new HashMap<>();
			health.put(STATUS, AppHealthStatus.OUT_OF_SERVICE.name());
			health.put(DESCRIPTION, e.getMessage());
		}

		return health;
	}

}
