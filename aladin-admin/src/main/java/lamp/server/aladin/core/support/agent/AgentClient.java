package lamp.server.aladin.core.support.agent;

import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.dto.AppDto;
import lamp.server.aladin.utils.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class AgentClient {

	@Getter
	private RestTemplate restTemplate;

	public AgentClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


	public List<AppDto> getAppList(Agent agent) {
		String baseUrl = agent.getProtocol() + "://" + agent.getAddress() + ":" + agent.getPort();
		ResponseEntity<List<AppDto>> responseEntity = restTemplate.exchange(baseUrl + "/api/app", HttpMethod.GET, null, new ParameterizedTypeReference<List<AppDto>>() {});

		return responseEntity.getBody();
	}

	public void register(Agent agent, AgentAppRegisterForm form) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = agent.getProtocol() + "://" + agent.getAddress() + ":" + agent.getPort();

			MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

			parts.add("id", form.getId());
			parts.add("name", StringUtils.utf8ToIso88591(form.getName()));
			parts.add("description", StringUtils.utf8ToIso88591(form.getDescription()));
			parts.add("artifactId", form.getArtifactId());
			parts.add("artifactName", StringUtils.utf8ToIso88591(form.getArtifactName()));
			parts.add("version", form.getVersion());
			parts.add("processType", form.getProcessType().name());
			parts.add("appDirectory", form.getAppDirectory());
			parts.add("workDirectory", form.getWorkDirectory());
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

	public void deregister(Agent agent, String appId) {
		String baseUrl = agent.getProtocol() + "://" + agent.getAddress() + ":" + agent.getPort();
		restTemplate.delete(baseUrl + "/api/app/" + appId);
	}

	public void start(Agent agent, String appId) {
		String baseUrl = agent.getProtocol() + "://" + agent.getAddress() + ":" + agent.getPort();
		ResponseEntity<Void> responseEntity = restTemplate.getForEntity(baseUrl + "/api/app/" + appId + "/start", Void.class);
	}

	public void stop(Agent agent, String appId) {
		String baseUrl = agent.getProtocol() + "://" + agent.getAddress() + ":" + agent.getPort();
		ResponseEntity<Void> responseEntity = restTemplate.getForEntity(baseUrl + "/api/app/" + appId + "/stop", Void.class);
	}


}
