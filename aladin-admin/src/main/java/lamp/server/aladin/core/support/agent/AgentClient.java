package lamp.server.aladin.core.support.agent;

import lamp.server.aladin.core.domain.Agent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class AgentClient {

	private RestTemplate restTemplate;

	public AgentClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void register(Agent agent, AgentAppRegisterForm form) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = agent.getProtocol() + "://" + agent.getAddress() + ":" + agent.getPort();
			MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

			parts.add("id", form.getId());
			parts.add("name", form.getName());
			parts.add("appId", form.getAppId());
			parts.add("appName", form.getAppName());
			parts.add("appVersion", form.getAppVersion());
			parts.add("processType", form.getProcessType().name());
			parts.add("pidFile", form.getPidFile());
			parts.add("startCommandLine", form.getStartCommandLine());
			parts.add("stopCommandLine", form.getStopCommandLine());
			parts.add("preInstalled", form.isPreInstalled());
			if (form.getInstallFile() != null) {
				parts.add("installFile", form.getInstallFile());
			}
			parts.add("filename", form.getFilename());
			parts.add("monitor", form.isMonitor());
			parts.add("commands", form.getCommands());

			log.info("parts = {}", parts);

			ResponseEntity<Void> responseEntity = restTemplate.postForEntity(baseUrl + "/api/app", parts, Void.class);
		} finally {
			AgentRequestUserHolder.clear();
		}


	}
}
