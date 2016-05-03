package lamp.collector.core.service.metrics;

import com.google.common.collect.Lists;
import lamp.collector.core.model.TargetMetricsType;
import lamp.collector.metrics.loader.rest.MetricsAndTagsMetricsLoader;
import lamp.common.collector.service.MetricsLoader;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lamp.collector.metrics.loader.rest.SpringBootMetricsLoader;
import lamp.common.collector.service.TargetMetricsListLoader;
import lamp.common.collector.service.TargetMetricsLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MetricsLoaderService {

	private Map<String, MetricsLoader> loaderMap;

	public MetricsLoaderService() {
		loaderMap = new HashMap<>();
		loaderMap.put(TargetMetricsType.SPRING_BOOT, new SpringBootMetricsLoader(new RestTemplate()));
		loaderMap.put(TargetMetricsType.METRICS_AND_TAGS, new MetricsAndTagsMetricsLoader(new RestTemplate()));
	}

	public List<TargetMetrics> getMetricsList(MetricsTarget metricsTarget) {
		log.debug("metricsType = {}", metricsTarget.getMetricsType());
		MetricsLoader loader = loaderMap.get(metricsTarget.getMetricsType());
		if (loader instanceof TargetMetricsLoader) {
			TargetMetrics targetMetrics = ((TargetMetricsLoader)loader).getMetrics(metricsTarget);
			return targetMetrics != null ? Lists.newArrayList(targetMetrics) : null;
		} else if (loader instanceof TargetMetricsListLoader) {
			return ((TargetMetricsListLoader) loader).getMetricsList(metricsTarget);
		}
		return null;
	}

}
