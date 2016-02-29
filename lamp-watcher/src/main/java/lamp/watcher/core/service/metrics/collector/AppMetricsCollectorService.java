package lamp.watcher.core.service.metrics.collector;

import lamp.watcher.core.domain.AppMetrics;
import lamp.watcher.core.domain.AppMetricsType;
import lamp.watcher.core.domain.WatchedApp;
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

	public AppMetrics getMetrics(WatchedApp watchedApp) {
		AppMetricsCollector collector = collectorMap.get(watchedApp.getMetricsType());
		return collector.getMetrics(watchedApp);
	}

}
