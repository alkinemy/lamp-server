package lamp.metrics.loader.rest;

import lamp.common.metrics.MetricsLoader;
import lamp.common.metrics.MetricsTagConstants;
import lamp.common.metrics.MetricsTarget;
import lamp.common.metrics.TargetMetrics;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class RestTemplateMetricsLoader implements MetricsLoader {

	private final RestTemplate restTemplate;

	public RestTemplateMetricsLoader() {
		this(new RestTemplate());
	}

	public RestTemplateMetricsLoader(RestTemplate restTemplate) {
		Objects.requireNonNull(restTemplate);

		this.restTemplate = restTemplate;
	}

	@Override
	public TargetMetrics getMetrics(MetricsTarget metricsTarget) {
		long timestamp = System.currentTimeMillis();

		String url = metricsTarget.getMetricsUrl();
		log.debug("metricsTarget = {}, url = {}", metricsTarget.getId(), url);

		Map<String, Object> metrics = restTemplate.getForObject(url, LinkedHashMap.class);
		log.debug("timestamp = {}, metrics = {}", timestamp, metrics);

		TargetMetrics targetMetrics = assemble(timestamp, metricsTarget, metrics);
		log.debug("targetMetrics = {}", targetMetrics);
		return targetMetrics;
	}


	protected TargetMetrics assemble(long timestamp, MetricsTarget metricsTarget, Map<String, Object> metrics) {
		Map<String, Object> assembledMetrics;
		String prefix = metricsTarget.getMetricsExportPrefix();
		if (StringUtils.isNotBlank(prefix)) {
			assembledMetrics = metrics.entrySet()
					.stream()
					.collect(Collectors.toMap(k -> prefix + k, Map.Entry::getValue));
		} else {
			assembledMetrics = metrics.entrySet()
					.stream()
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}

		Map<String, String> tags = new LinkedHashMap<>();
		tags.put(MetricsTagConstants.ID, metricsTarget.getId());
		tags.put(MetricsTagConstants.HOSTNAME, metricsTarget.getHostname());
		tags.put(MetricsTagConstants.ADDRESS, metricsTarget.getAddress());

		if (metricsTarget.getTags() != null) {
			tags.putAll(metricsTarget.getTags());
		}

		return new TargetMetrics(timestamp, metricsTarget.getId(), metricsTarget.getName(), assembledMetrics, tags);
	}

}
