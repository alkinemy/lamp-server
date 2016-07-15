package lamp.collector.metrics.loader.rest;

import lamp.collector.core.model.MetricsAndTags;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lamp.common.collector.service.TargetMetricsListLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Deprecated
public class MetricsAndTagsMetricsLoader implements TargetMetricsListLoader {

	private final RestTemplate restTemplate;

	public MetricsAndTagsMetricsLoader() {
		this(new RestTemplate());
	}

	public MetricsAndTagsMetricsLoader(RestTemplate restTemplate) {
		Objects.requireNonNull(restTemplate);

		this.restTemplate = restTemplate;
	}

	@Override
	public List<TargetMetrics> getMetricsList(MetricsTarget metricsTarget) {
		String url = metricsTarget.getMetricsUrl();
		log.debug("metricsTarget = {}, url = {}", metricsTarget.getId(), url);

		ParameterizedTypeReference<List<MetricsAndTags>> typeRef = new ParameterizedTypeReference<List<MetricsAndTags>>() {};
		ResponseEntity<List<MetricsAndTags>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
		List<MetricsAndTags> metricsAndTagsList = responseEntity.getBody();

		List<TargetMetrics> targetMetricsList = assemble(metricsTarget, metricsAndTagsList);
		log.debug("targetMetricsList = {}", targetMetricsList);
		return targetMetricsList;
	}


	protected List<TargetMetrics> assemble(MetricsTarget metricsTarget, List<MetricsAndTags> metricsAndTagsList) {
		return metricsAndTagsList.stream().map(metricsAndTags ->
			new TargetMetrics(metricsAndTags.getTimestamp(),
							  metricsTarget.getId(), metricsTarget.getName(), metricsTarget.getGroupId(), metricsTarget.getArtifactId(),
							  metricsAndTags.getMetrics(), metricsAndTags.getTags())
		).collect(Collectors.toList());
	}

}
