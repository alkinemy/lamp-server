package lamp.admin.domain.support.agent;

import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.app.model.AppDto;
import lamp.admin.domain.app.model.LogFile;
import lamp.admin.domain.base.model.JavaVirtualMachine;
import lamp.admin.domain.support.agent.model.AgentAppRegisterForm;
import lamp.admin.domain.support.agent.model.AgentAppFileUpdateForm;
import lamp.admin.domain.support.agent.model.AgentAppUpdateSpecForm;
import lamp.admin.domain.support.agent.security.AgentRequestUser;
import lamp.admin.domain.support.agent.security.AgentRequestUserHolder;
import lamp.common.utils.IOUtils;
import lamp.common.utils.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.OutputStream;
import java.util.List;

@Slf4j
public class AgentClient {

	@Getter
	private RestTemplate restTemplate;

	public AgentClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


	public <T> T getForObject(Agent agent, String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			return restTemplate.getForObject(url, responseType, urlVariables);
		} finally {
			AgentRequestUserHolder.clear();
		}
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
			parts.add("logDirectory", form.getLogDirectory());
			parts.add("pidFile", form.getPidFile());
			parts.add("ptql", form.getPtql());
			parts.add("stdOutFile", form.getStdOutFile());
			parts.add("stdErrFile", form.getStdErrFile());
			parts.add("startCommandLine", form.getStartCommandLine());
			parts.add("stopCommandLine", form.getStopCommandLine());
			parts.add("preInstalled", form.isPreInstalled());
			if (form.getInstallFile() != null) {
				parts.add("installFile", form.getInstallFile());
			}
			parts.add("filename", form.getFilename());
			parts.add("monitor", form.isMonitor());
			parts.add("commands", StringUtils.utf8ToIso88591(form.getCommands()));

			if (form.getParametersType() != null) {
				parts.add("parametersType", form.getParametersType().name());
			}

			parts.add("parameters", StringUtils.utf8ToIso88591(form.getParameters()));

			log.debug("parts = {}", parts);

			ResponseEntity<Void> responseEntity = restTemplate.postForEntity(baseUrl + "/api/app", parts, Void.class);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void updateFile(Agent agent, AgentAppFileUpdateForm form) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);

			MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

			parts.add("id", form.getId());
			parts.add("version", form.getVersion());
			parts.add("installFile", form.getInstallFile());

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


	public void deregister(Agent agent, String appId, boolean forceStop) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			restTemplate.delete(baseUrl + "/api/app/" + appId + "?forceStop=" + forceStop);
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


	// Extenstion

	public List<JavaVirtualMachine> getVmList(Agent agent) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<List<JavaVirtualMachine>> responseEntity = restTemplate.exchange(baseUrl + "/api/vm", HttpMethod.GET, null, new ParameterizedTypeReference<List<JavaVirtualMachine>>() {});

			return responseEntity.getBody();
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public List<LogFile> getLogFiles(Agent agent, String appId) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<List<LogFile>> responseEntity = restTemplate.exchange(baseUrl + "/api/app/{appId}/log", HttpMethod.GET, null, new ParameterizedTypeReference<List<LogFile>>() {}, appId);

			return responseEntity.getBody();
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void transferLogFile(Agent agent, String appId, String filename, final OutputStream outputStream) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			RequestCallback requestCallback = request -> {};
			ResponseExtractor<Void> responseExtractor = response -> {
				IOUtils.copy(response.getBody(), outputStream);
				return null;
			};
			restTemplate.execute(baseUrl + "/api/app/{appId}/log/{filename}", HttpMethod.GET, requestCallback, responseExtractor, appId, filename);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

}
