package lamp.collector.core.service.metrics.collector;

import lamp.collector.core.domain.TargetMetrics;
import lamp.collector.core.domain.TargetMetricsType;
import lamp.collector.core.domain.CollectionTarget;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MetricsCollectorService {

	private Map<String, MetricsCollector> collectorMap;

	public MetricsCollectorService() {
		// TODO 수정해라
		collectorMap = new HashMap<>();
		collectorMap.put(TargetMetricsType.SPRING_BOOT, new MetricsRestTemplateCollector());
	}

	public TargetMetrics getMetrics(CollectionTarget collectionTarget) {
		MetricsCollector collector = collectorMap.get(collectionTarget.getMetricsType());
		return collector.getMetrics(collectionTarget);
	}

}
