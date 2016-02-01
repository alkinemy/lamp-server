package lamp.server.watch.core.service;

import lamp.server.aladin.utils.StringUtils;
import lamp.server.watch.core.domain.WatchedApp;
import lamp.server.watch.core.domain.HealthStatus;
import lamp.server.watch.core.domain.HealthStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AppHealthCheckService {

	private static final String CODE = "status";
	private static final String DESCRIPTION = "description";

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private AppStatusService appStatusService;

	@Async
	public void checkHealth(WatchedApp watchedApp) {
		String url = watchedApp.getHealthUrl();
		log.debug("app = {}, url = {}", watchedApp.getId(), url);
		if (StringUtils.isNotBlank(url)) {
			Map<String, Object> health = getHealth(url);
			log.debug("health = {}", health);

			String code = (String) health.get(CODE);
			String description = (String) health.get(DESCRIPTION);
			appStatusService.updateStatus(watchedApp, HealthStatus.of(code, description), LocalDateTime.now());
		}
	}

	protected Map<String, Object> getHealth(String url) {
		Map<String, Object> health;
		try {
			health = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			log.warn("health failed", e);
			health = new HashMap<>();
			health.put(CODE, HealthStatusCode.OUT_OF_SERVICE.name());
			health.put(DESCRIPTION, e.getMessage());
		}

		return health;
	}

}
