package lamp.watcher.core.service.health.collector;

import lamp.watcher.core.domain.AppHealth;
import lamp.watcher.core.domain.AppHealthType;
import lamp.watcher.core.domain.WatchedApp;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppHealthCollectorService {

	private Map<String, AppHealthCollector> collectorMap;

	public AppHealthCollectorService() {
		// TODO 수정해라
		collectorMap = new HashMap<>();
		collectorMap.put(AppHealthType.SPRING_BOOT, new AppHealthSpringBootCollector());
	}

	public AppHealth getHealth(WatchedApp watchedApp) {
		AppHealthCollector collector = collectorMap.get(watchedApp.getHealthType());
		return collector.getHealth(watchedApp);
	}

}
