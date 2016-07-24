package lamp.admin.domain.agent.service;

import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.app.base.AppInstanceStatus;
import lamp.admin.core.app.base.AppInstanceStatusResult;
import lamp.admin.core.app.base.HealthEndpoint;
import lamp.admin.core.host.Host;
import lamp.admin.domain.host.service.HostFacadeService;
import lamp.common.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AgentAppInstanceHealthChecker {

	@Autowired
	private HostFacadeService hostFacadeService;

	public AppInstanceStatusResult getStatusResult(AppInstance appInstance, HealthEndpoint healthEndpoint) {
		// FIXME
		AppInstanceStatusResult statusResult;
		try {
			Host host = hostFacadeService.getHost(appInstance.getHostId());
			String url = getHealthCheckUrl(healthEndpoint, host.getAddress());
			log.debug("AppInstance HealthCheckUrl = {}", url);

			RestTemplate restTemplate = newRestTemplate(healthEndpoint);

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

	protected RestTemplate newRestTemplate(HealthEndpoint healthEndpoint) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(healthEndpoint.getTimeoutSeconds() * 1000);
		clientHttpRequestFactory.setConnectionRequestTimeout(healthEndpoint.getTimeoutSeconds() * 1000);
		clientHttpRequestFactory.setReadTimeout(healthEndpoint.getTimeoutSeconds() * 1000);
		return new RestTemplate(clientHttpRequestFactory);
	}

	protected String getHealthCheckUrl(HealthEndpoint healthEndpoint, String address) {
		StringBuilder url = new StringBuilder();
		url.append(healthEndpoint.getProtocol().toString().toLowerCase()).append("://");
		url.append(address).append(":").append(healthEndpoint.getPort());
		url.append(healthEndpoint.getPath());
		return url.toString();
	}
}
