package lamp.collector.core.service.health.collector;

import lamp.collector.core.domain.AppHealth;
import lamp.collector.core.domain.AppHealthType;
import lamp.collector.core.domain.TargetApp;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppHealthCollectorService {

	private Map<String, AppHealthCollector> collectorMap;

	public AppHealthCollectorService() {
		// TODO 수정해라
		collectorMap = new HashMap<>();
		collectorMap.put(AppHealthType.SPRING_BOOT, new AppHealthRestTemplateCollector());
	}

	public AppHealth getHealth(TargetApp targetApp) {
		AppHealthCollector collector = collectorMap.get(targetApp.getHealthType());
		return collector.getHealth(targetApp);
	}

}
