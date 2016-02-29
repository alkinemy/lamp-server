package lamp.watcher.core.service.health.collector;

import lamp.watcher.core.domain.AppHealth;
import lamp.watcher.core.domain.AppMetrics;
import lamp.watcher.core.domain.WatchedApp;

public interface AppHealthCollector {

	AppHealth getHealth(WatchedApp watchedApp);

}
