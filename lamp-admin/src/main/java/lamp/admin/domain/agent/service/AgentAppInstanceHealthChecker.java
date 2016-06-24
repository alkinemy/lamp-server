package lamp.admin.domain.agent.service;

import lamp.admin.core.agent.AgentHttpRequestInterceptor;
import lamp.admin.core.agent.AgentResponseErrorHandler;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.app.base.AppInstanceStatus;
import lamp.admin.core.app.base.AppInstanceStatusResult;
import lamp.admin.core.app.base.HealthCheck;
import lamp.admin.core.host.Host;
import lamp.common.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AgentAppInstanceHealthChecker {

	public AppInstanceStatusResult getStatusResult(AppInstance appInstance, HealthCheck healthCheck) {
		// FIXME
		AppInstanceStatusResult statusResult;
		try {
			Host host = appInstance.getHost();
			String url = getHealthCheckUrl(healthCheck, host.getAddress());
			log.debug("AppInstance HealthCheckUrl = {}", url);

			RestTemplate restTemplate = newRestTemplate(healthCheck);

			ResponseEntity<Void> responseEntity = restTemplate.getForEntity(url, Void.class);
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				statusResult = new AppInstanceStatusResult(AppInstanceStatus.RUNNING);
			} else {
				statusResult = new AppInstanceStatusResult(AppInstanceStatus.UNKNOWN, "StatusCode = " + responseEntity.getStatusCode().value());
			}
		} catch (Exception e) {
			statusResult = new AppInstanceStatusResult(AppInstanceStatus.STOPPED, ExceptionUtils.getStackTrace(e, 2000));
		}
		return statusResult;
	}

	protected RestTemplate newRestTemplate(HealthCheck healthCheck) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(healthCheck.getTimeoutSeconds() * 1000);
		clientHttpRequestFactory.setConnectionRequestTimeout(healthCheck.getTimeoutSeconds() * 1000);
		clientHttpRequestFactory.setReadTimeout(healthCheck.getTimeoutSeconds() * 1000);
		return new RestTemplate(clientHttpRequestFactory);
	}

	protected String getHealthCheckUrl(HealthCheck healthCheck, String address) {
		StringBuilder url = new StringBuilder();
		url.append(healthCheck.getProtocol()).append("://");
		url.append(address).append(":").append(healthCheck.getPort());
		url.append(healthCheck.getPath());
		return url.toString();
	}
}
