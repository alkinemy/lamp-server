package lamp.collector.core.service.metrics.collector;

import lamp.collector.core.domain.AppMetrics;
import lamp.collector.core.domain.WatchedApp;

public interface AppMetricsCollector {

	AppMetrics getMetrics(WatchedApp watchedApp);

}
