package lamp.collector.core.service.health.collector;

import lamp.collector.core.domain.AppHealth;
import lamp.collector.core.domain.TargetApp;

public interface AppHealthCollector {

	AppHealth getHealth(TargetApp targetApp);

}
