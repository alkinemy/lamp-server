package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
public class AgentHealthCheckService {

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private TargetServerStatusService targetServerStatusService;

	@Async
	public void checkHealth(TargetServer targetServer) {
		String url = targetServer.getAgentHealthUrl();
		log.debug("targetServer = {}, url = {}", targetServer.getHostname(), url);
		if (StringUtils.isNotBlank(url)) {
			Map<String, Object> health = restTemplate.getForObject(url, Map.class);
			String agentStatus = (String) health.get("status");
			LocalDateTime agentStatusDate = LocalDateTime.now();
			log.debug("health = {}", health);
			targetServerStatusService.updateStatus(targetServer, agentStatus, agentStatusDate);
		}
	}

}
