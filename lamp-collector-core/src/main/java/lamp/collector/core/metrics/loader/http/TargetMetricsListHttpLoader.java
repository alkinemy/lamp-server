package lamp.collector.core.metrics.loader.http;


import lamp.collector.core.base.Endpoint;
import lamp.collector.core.base.loader.RestTemplateHttpLoader;
import lamp.collector.core.metrics.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class TargetMetricsListHttpLoader extends RestTemplateHttpLoader {

	public List<TargetMetrics> getTargetMetricsList(Endpoint endpoint) {
		String url = getUrl(endpoint);
		RestTemplate restTemplate = getRestTemplate(endpoint);

		ParameterizedTypeReference<List<TargetMetrics>> typeRef = new ParameterizedTypeReference<List<TargetMetrics>>() {};
		ResponseEntity<List<TargetMetrics>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
		return responseEntity.getBody();
	}

}
