package lamp.admin.core.monitoring.service;

import lamp.admin.core.agent.domain.TargetServer;
import lamp.admin.core.agent.service.TargetServerStatusService;
import lamp.admin.core.monitoring.domain.HealthStatus;
import lamp.admin.core.monitoring.domain.HealthStatusCode;
import lamp.admin.core.support.agent.AgentClient;
import lamp.admin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AgentHealthCheckService {

	private static final String CODE = "status";
	private static final String DESCRIPTION = "description";

	@Autowired
	private AgentClient agentClient;

	@Autowired
	private TargetServerStatusService targetServerStatusService;

	@Async
	public void checkHealth(TargetServer targetServer) {
		String url = targetServer.getAgentHealthUrl();
		log.debug("targetServer = {}, url = {}", targetServer.getHostname(), url);
		if (StringUtils.isNotBlank(url)) {
			Map<String, Object> health = getHealth(url);
			HealthStatus healthStatus = getHealthStatus(health);
			LocalDateTime healthStatusDate = LocalDateTime.now();
			log.debug("health = {}", health);
			// {"status":"UP","diskSpace":{"status":"UP","total":249769230336,"free":215190552576,"threshold":10485760},"db":{"status":"UP","database":"MySQL","hello":1}}
//			{"status":"DOWN","diskSpace":{"status":"DOWN","total":249769230336,"free":214708588544,"threshold":249769230336},"db":{"status":"UP","database":"MySQL","hello":1}}
//			List<HealthStatus> healthStatusList = health.entrySet().stream()
//					.filter(e -> !CODE.equals(e.getKey()))
//					.map(Map.Entry::getValue).map(this::getHealthStatus)
//					.filter(s -> s != null).collect(Collectors.toList());

			targetServerStatusService.updateStatus(targetServer, healthStatus, healthStatusDate);
		}
	}

	protected HealthStatus getHealthStatus(Object health) {
		if (health instanceof Map) {
			return getHealthStatus((Map) health);
		}
		return null;
	}

	protected HealthStatus getHealthStatus(Map<String, Object> health) {
		String code = (String) health.get(CODE);
		String description = (String) health.get(DESCRIPTION);
		return HealthStatus.of(code, description);
	}

	protected Map<String, Object> getHealth(String url) {
		Map<String, Object> health;
		try {
			health = agentClient.getRestTemplate().getForObject(url, Map.class);
		} catch (Exception e) {
			log.warn("health failed", e);
			health = new HashMap<>();
			health.put(CODE, HealthStatusCode.OUT_OF_SERVICE.name());
			health.put(DESCRIPTION, e.getMessage());
		}

		return health;
	}

}
