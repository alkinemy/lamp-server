package lamp.admin.core.agent;

import lamp.admin.core.agent.security.AgentRequestUser;
import lamp.admin.core.agent.security.AgentRequestUserHolder;
import lamp.admin.core.app.base.AppContainer;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

	public List<AppInstance> getAppInstances(Agent agent) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<List<AppInstance>> responseEntity = restTemplate.exchange(baseUrl + "/api/app", HttpMethod.GET, null, new ParameterizedTypeReference<List<AppInstance>>() {});

			return Optional.ofNullable(responseEntity.getBody()).orElse(Collections.emptyList());
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public AppInstance getAppInstance(Agent agent, String instanceId) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<AppInstance> responseEntity = restTemplate.getForEntity(baseUrl + "/api/app/{instanceId}", AppInstance.class, instanceId);

			return responseEntity.getBody();
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void deployApp(Agent agent, String appId, AppContainer appContainer, Resource resource)  {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);

			MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

			parts.add("appId", appId);
			parts.add("appContainer", StringUtils.utf8ToIso88591(JsonUtils.stringify(appContainer)));

			if (resource != null) {
				parts.add("resource", resource);
			}

			log.debug("parts = {}", parts);

			ResponseEntity<Void> responseEntity = restTemplate.postForEntity(baseUrl + "/api/app", parts, Void.class);
		} finally {
			AgentRequestUserHolder.clear();
		}

	}

	public void undeployApp(Agent agent, String instanceId, boolean forceStop) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			restTemplate.delete(baseUrl + "/api/app/{instanceId}?forceStop={forceStop}", instanceId, forceStop);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void start(Agent agent, String instanceId) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<Void> responseEntity = restTemplate.getForEntity(baseUrl + "/api/app/{instanceId}/start", Void.class, instanceId);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	public void stop(Agent agent, String instanceId) {
		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
		try {
			String baseUrl = getBaseUrl(agent);
			ResponseEntity<Void> responseEntity = restTemplate.getForEntity(baseUrl + "/api/app/{instanceId}/stop", Void.class, instanceId);
		} finally {
			AgentRequestUserHolder.clear();
		}
	}

	protected String getBaseUrl(Agent agent) {
		return agent.getProtocol() + "://" + agent.getAddress() + ":" + agent.getPort();
	}


	// Extenstion

//	public List<JavaVirtualMachine> getVmList(Agent agent) {
//		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
//		try {
//			String baseUrl = getBaseUrl(agent);
//			ResponseEntity<List<JavaVirtualMachine>> responseEntity = restTemplate.exchange(baseUrl + "/api/vm", HttpMethod.GET, null, new ParameterizedTypeReference<List<JavaVirtualMachine>>() {});
//
//			return responseEntity.getBody();
//		} finally {
//			AgentRequestUserHolder.clear();
//		}
//	}
//
//	public List<LogFile> getLogFiles(Agent agent, String appId) {
//		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
//		try {
//			String baseUrl = getBaseUrl(agent);
//			ResponseEntity<List<LogFile>> responseEntity = restTemplate.exchange(baseUrl + "/api/app/{appId}/log", HttpMethod.GET, null, new ParameterizedTypeReference<List<LogFile>>() {}, appId);
//
//			return responseEntity.getBody();
//		} finally {
//			AgentRequestUserHolder.clear();
//		}
//	}
//
//	public void transferLogFile(Agent agent, String appId, String filename, final OutputStream outputStream) {
//		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
//		try {
//			String baseUrl = getBaseUrl(agent);
//			RequestCallback requestCallback = request -> {};
//			ResponseExtractor<Void> responseExtractor = response -> {
//				IOUtils.copy(response.getBody(), outputStream);
//				return null;
//			};
//			restTemplate.execute(baseUrl + "/api/app/{appId}/log/{filename}", HttpMethod.GET, requestCallback, responseExtractor, appId, filename);
//		} finally {
//			AgentRequestUserHolder.clear();
//		}
//	}
//
//	public void deployApp(Agent agent, DockerApp dockerApp) {
//		log.info("dockerApp = {}", dockerApp);
//		AgentRequestUserHolder.setRequestUser(AgentRequestUser.of(agent.getId(), agent.getSecretKey()));
//		try {
//			String baseUrl = getBaseUrl(agent);
//
//			ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl + "/api/container", dockerApp, String.class);
//			log.info("response : {}", responseEntity.getBody());
//		} finally {
//			AgentRequestUserHolder.clear();
//		}
//	}
}
