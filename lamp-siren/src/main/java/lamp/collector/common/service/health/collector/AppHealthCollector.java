package lamp.collector.common.service.health.collector;

import lamp.collector.common.domain.AppHealth;
import lamp.collector.common.domain.WatchedApp;

public interface AppHealthCollector {

	AppHealth getHealth(WatchedApp watchedApp);

}
