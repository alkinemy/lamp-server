package lamp.metrics.loader.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class SpringBootHealthLoader extends RestTemplateHealthLoader {

	private ObjectMapper objectMapper = new ObjectMapper();

	public SpringBootHealthLoader(RestTemplate restTemplate) {
		super(restTemplate);
	}

	@Override
	protected Map<String, Object> getHealth(String url) {

		try {
			return super.getHealth(url);
		} catch (HttpServerErrorException e) {
			try {
				return objectMapper.readValue(e.getResponseBodyAsByteArray(), LinkedHashMap.class);
			} catch (Exception ex) {
				Map<String, Object> health = new LinkedHashMap<>();
				health.put("status", SpringBootHealthStatus.DOWN.name());
				health.put("description", e.getMessage());
				return health;
			}
		} catch (Exception e) {
			Map<String, Object> health = new LinkedHashMap<>();
			health.put("status", SpringBootHealthStatus.UNKNOWN.name());
			health.put("description", e.getMessage());
			return health;
		}

	}


}
