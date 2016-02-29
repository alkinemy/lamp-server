package lamp.watcher.core.service.metrics.collector;

import lamp.watcher.core.domain.AppMetrics;
import lamp.watcher.core.domain.WatchedApp;

public interface AppMetricsCollector {

	AppMetrics getMetrics(WatchedApp watchedApp);

}
