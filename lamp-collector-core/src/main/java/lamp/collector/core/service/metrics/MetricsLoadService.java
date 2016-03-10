package lamp.collector.core.service.metrics;

import lamp.collector.core.domain.TargetMetricsType;
import lamp.common.collection.CollectionTarget;
import lamp.common.metrics.MetricsLoader;
import lamp.common.metrics.TargetMetrics;
import lamp.metrics.loader.rest.SpringBootMetricsLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MetricsLoadService {

	private Map<String, MetricsLoader> loaderMap;

	public MetricsLoadService() {
		loaderMap = new HashMap<>();
		loaderMap.put(TargetMetricsType.SPRING_BOOT, new SpringBootMetricsLoader(new RestTemplate()));
	}

	public TargetMetrics getMetrics(CollectionTarget collectionTarget) {
		MetricsLoader loader = loaderMap.get(collectionTarget.getMetricsType());
		return loader.getMetrics(collectionTarget);
	}

}
