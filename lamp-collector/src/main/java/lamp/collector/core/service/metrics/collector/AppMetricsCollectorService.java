package lamp.collector.core.service.metrics.collector;

import lamp.collector.core.domain.AppMetrics;
import lamp.collector.core.domain.AppMetricsType;
import lamp.collector.core.domain.TargetApp;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppMetricsCollectorService {

	private Map<String, AppMetricsCollector> collectorMap;

	public AppMetricsCollectorService() {
		// TODO 수정해라
		collectorMap = new HashMap<>();
		collectorMap.put(AppMetricsType.SPRING_BOOT, new AppMetricsRestTemplateCollector());
	}

	public AppMetrics getMetrics(TargetApp targetApp) {
		AppMetricsCollector collector = collectorMap.get(targetApp.getMetricsType());
		return collector.getMetrics(targetApp);
	}

}
