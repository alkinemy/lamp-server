package lamp.collector.common.service.metrics.collector;

import lamp.collector.common.domain.AppMetrics;
import lamp.collector.common.domain.AppMetricsType;
import lamp.collector.common.domain.MonitoringTarget;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppMetricsCollectorService {

	private Map<String, AppMetricsCollector> collectorMap;

	public AppMetricsCollectorService() {
		// TODO 수정해라
		collectorMap = new HashMap<>();
		collectorMap.put(AppMetricsType.SPRING_BOOT, new AppMetricsSpringBootCollector());
	}

	public AppMetrics getMetrics(MonitoringTarget watchedApp) {
		AppMetricsCollector collector = collectorMap.get(watchedApp.getMetricsType());
		return collector.getMetrics(watchedApp);
	}

}
