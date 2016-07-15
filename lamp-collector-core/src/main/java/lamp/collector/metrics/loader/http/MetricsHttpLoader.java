package lamp.collector.metrics.loader.http;

import lamp.collector.core.Endpoint;
import lamp.collector.core.loader.RestTemplateHttpLoader;
import lamp.collector.metrics.loader.MetricsLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class MetricsHttpLoader extends RestTemplateHttpLoader implements MetricsLoader {

	@Override
	public Map<String, Object> getMetrics(Endpoint endpoint) {
		String url = getUrl(endpoint);
		RestTemplate restTemplate = getRestTemplate(endpoint);

		Map<String, Object> metrics = restTemplate.getForObject(url, LinkedHashMap.class);

		return metrics;
	}


}
