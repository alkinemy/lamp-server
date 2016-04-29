package lamp.watcher.repository;

import lamp.common.collector.model.HealthTarget;
import lamp.watcher.config.LampServerProperties;
import lamp.watcher.model.WatchTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class WatchTargetRepository {

	@Autowired
	private LampServerProperties lampServerProperties;
	@Autowired
	private RestTemplate restTemplate;

	public List<WatchTarget> findAll() {
		String url = lampServerProperties.getUrl() + "/api/watch-target";
		ResponseEntity<List<WatchTarget>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<WatchTarget>>() {});

		return responseEntity.getBody();
	}

}
