package lamp.collector.common.service.health.collector;

import lamp.collector.common.domain.AppHealth;
import lamp.collector.common.domain.AppHealthType;
import lamp.collector.common.domain.MonitoringTarget;
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

	public AppHealth getHealth(MonitoringTarget watchedApp) {
		AppHealthCollector collector = collectorMap.get(watchedApp.getHealthType());
		return collector.getHealth(watchedApp);
	}

}
