package lamp.collector.core.service.health.collector;

import lamp.collector.core.domain.AppHealth;
import lamp.collector.core.domain.WatchedApp;

public interface AppHealthCollector {

	AppHealth getHealth(WatchedApp watchedApp);

}
