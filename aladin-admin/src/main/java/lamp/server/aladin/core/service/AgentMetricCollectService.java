package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class AgentMetricCollectService {

	private RestTemplate restTemplate = new RestTemplate();

	@Async
	public void collectMetrics(Agent agent) {
		log.info("agent = {}", agent);
		String url = agent.getMetricsUrl();
		log.info("url = {}", url);
		if (StringUtils.isNotBlank(url)) {
			Map<String, Object> metrics = restTemplate.getForObject(url, Map.class);
			log.info("metrics = {}", metrics);
		}
	}

}
