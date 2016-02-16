package lamp.admin.core.support.agent;

import lamp.admin.core.agent.domain.Agent;
import lamp.admin.core.app.domain.AppDto;
import lamp.admin.core.support.agent.model.AgentAppRegisterForm;
import lamp.admin.core.support.agent.model.AgentAppUpdateFileForm;
import lamp.admin.core.support.agent.model.AgentAppUpdateSpecForm;
import lamp.admin.core.support.agent.security.AgentRequestUser;
import lamp.admin.core.support.agent.security.AgentRequestUserHolder;
import lamp.admin.utils.StringUtils;
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
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<List<AppDto>> responseEntity = restTemplate.exchange(baseUrl + "/api/app", HttpMethod.GET, null, new ParameterizedTypeReference<List<AppDto>>() {});

			return responseEntity.getBody();
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public AppDto getApp(Agent agent, String appId) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<AppDto> responseEntity = restTemplate.getForEntity(baseUrl + "/api/app/{appId}", AppDto.class, appId);

			return responseEntity.getBody();
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void register(Agent agent, AgentAppRegisterForm form) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);

			MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

			parts.add("id", form.getId());
			parts.add("name", StringUtils.utf8ToIso88591(form.getName()));
			parts.add("description", StringUtils.utf8ToIso88591(form.getDescription()));
			parts.add("groupId", form.getGroupId());
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

			log.debug("parts = {}", parts);

			ResponseEntity<Void> responseEntity = restTemplate.postForEntity(baseUrl + "/api/app", parts, Void.class);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void updateFile(Agent agent, AgentAppUpdateFileForm form) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);

			MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

			parts.add("id", form.getId());
			parts.add("artifactId", form.getArtifactId());
			parts.add("artifactName", StringUtils.utf8ToIso88591(form.getArtifactName()));
			parts.add("version", form.getVersion());
			parts.add("installFile", form.getInstallFile());
			parts.add("commands", form.getCommands());

			log.debug("parts = {}", parts);

			ResponseEntity<Void> responseEntity = restTemplate.postForEntity(baseUrl + "/api/app/{id}/file", parts, Void.class, form.getId());
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void updateSpec(Agent agent, AgentAppUpdateSpecForm form) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);

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

			log.debug("parts = {}", parts);

			ResponseEntity<Void> responseEntity = restTemplate.postForEntity(baseUrl + "/api/app/{id}", parts, Void.class, form);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}


	public void deregister(Agent agent, String appId) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			restTemplate.delete(baseUrl + "/api/app/" + appId);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void start(Agent agent, String appId) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<Void> responseEntity = restTemplate.getForEntity(baseUrl + "/api/app/" + appId + "/start", Void.class);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void stop(Agent agent, String appId) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<Void> responseEntity = restTemplate.getForEntity(baseUrl + "/api/app/" + appId + "/stop", Void.class);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	protected String getBaseUrl(Agent agent) {
		return agent.getProtocol() + "://" + agent.getAddress() + ":" + agent.getPort();
	}


}
