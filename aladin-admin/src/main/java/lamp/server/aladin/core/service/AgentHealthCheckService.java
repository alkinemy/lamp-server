package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.HealthStatus;
import lamp.server.aladin.core.domain.HealthStatusCode;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.utils.StringUtils;
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
public class AgentHealthCheckService {

	private static final String CODE = "status";
	private static final String DESCRIPTION = "description";

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private TargetServerStatusService targetServerStatusService;

	@Async
	public void checkHealth(TargetServer targetServer) {
		String url = targetServer.getAgentHealthUrl();
		log.debug("targetServer = {}, url = {}", targetServer.getHostname(), url);
		if (StringUtils.isNotBlank(url)) {
			Map<String, Object> health = getHealth(url);
			String code = (String) health.get(CODE);
			String description = (String) health.get(DESCRIPTION);
			HealthStatus healthStatus = HealthStatus.of(code, description);
			LocalDateTime agentStatusDate = LocalDateTime.now();
			log.debug("health = {}", health);
			targetServerStatusService.updateStatus(targetServer, healthStatus, agentStatusDate);
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
