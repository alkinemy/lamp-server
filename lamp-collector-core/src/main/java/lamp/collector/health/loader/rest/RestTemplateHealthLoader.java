package lamp.collector.health.loader.rest;

import lamp.common.collector.service.HealthLoader;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.MetricsTagConstants;
import lamp.common.collector.model.TargetHealth;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class RestTemplateHealthLoader implements HealthLoader {

	private RestTemplate restTemplate;

	public RestTemplateHealthLoader() {
		this(new RestTemplate());
	}

	public RestTemplateHealthLoader(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public TargetHealth getHealth(HealthTarget healthTarget) {
		long timestamp = System.currentTimeMillis();

		String url = healthTarget.getHealthUrl();
		log.debug("healthTarget = {}, url = {}", healthTarget.getId(), url);

		Map<String, Object> health = getHealth(url);
		log.debug("timestamp = {}, health = {}", timestamp, health);

		TargetHealth targetHealth = assemble(timestamp, healthTarget, health, healthTarget.getHealthExportPrefix());
		log.debug("targetHealth = {}", targetHealth);
		return targetHealth;
	}

	protected Map<String, Object> getHealth(String url) {
		return restTemplate.getForObject(url, LinkedHashMap.class);
	}

	protected TargetHealth assemble(long timestamp, HealthTarget healthTarget, Map<String, Object> health, String healthExportPrefix) {
		Map<String, Object> assembledHealth;
		String prefix = healthTarget.getHealthExportPrefix();
		if (StringUtils.isNotBlank(prefix)) {
			assembledHealth = health.entrySet()
				.stream()
				.collect(Collectors.toMap(k -> prefix + k, Map.Entry::getValue));
		} else {
			assembledHealth = health.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}

		Map<String, String> tags = new LinkedHashMap<>();
		tags.put(MetricsTagConstants.ID, healthTarget.getId());
		tags.put(MetricsTagConstants.HOSTNAME, healthTarget.getHostname());
		tags.put(MetricsTagConstants.ADDRESS, healthTarget.getAddress());

		if (healthTarget.getTags() != null) {
			tags.putAll(healthTarget.getTags());
		}

		return new TargetHealth(timestamp, healthTarget.getId(), healthTarget.getName(), healthTarget.getGroupId(), healthTarget.getArtifactId(), assembledHealth, tags);
	}

}
