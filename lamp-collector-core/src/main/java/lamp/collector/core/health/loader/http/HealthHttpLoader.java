package lamp.collector.core.health.loader.http;

import lamp.collector.core.base.Endpoint;
import lamp.collector.core.base.loader.RestTemplateHttpLoader;
import lamp.collector.core.health.HealthStatus;
import lamp.collector.core.health.HealthStatusCode;
import lamp.collector.core.health.loader.HealthLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HealthHttpLoader extends RestTemplateHttpLoader implements HealthLoader {

	@Override
	public HealthStatus getHealth(Endpoint endpoint) {
		String url = getUrl(endpoint);
		RestTemplate restTemplate = getRestTemplate(endpoint);

		try {
			String responseBody = restTemplate.getForObject(url, String.class);
			return new HealthStatus(HealthStatusCode.UP, null);
		} catch (ResourceAccessException e) {
			return new HealthStatus(HealthStatusCode.OUT_OF_SERVICE, e.getMessage());
		} catch (HttpServerErrorException e) {
			return new HealthStatus(HealthStatusCode.OUT_OF_SERVICE, e.getMessage());
		} catch (Exception e) {
			return new HealthStatus(HealthStatusCode.UNKNOWN, e.getMessage());
		}
	}

}
