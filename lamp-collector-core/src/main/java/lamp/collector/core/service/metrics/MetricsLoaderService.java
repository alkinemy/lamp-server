package lamp.collector.core.service.metrics;

import lamp.collector.core.model.TargetMetricsType;
import lamp.common.collector.service.MetricsLoader;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lamp.metrics.loader.rest.SpringBootMetricsLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MetricsLoaderService {

	private Map<String, MetricsLoader> loaderMap;

	public MetricsLoaderService() {
		loaderMap = new HashMap<>();
		loaderMap.put(TargetMetricsType.SPRING_BOOT, new SpringBootMetricsLoader(new RestTemplate()));
	}

	public TargetMetrics getMetrics(MetricsTarget metricsTarget) {
		log.debug("metricsType = {}", metricsTarget.getMetricsType());
		MetricsLoader loader = loaderMap.get(metricsTarget.getMetricsType());
		return loader.getMetrics(metricsTarget);
	}

}
