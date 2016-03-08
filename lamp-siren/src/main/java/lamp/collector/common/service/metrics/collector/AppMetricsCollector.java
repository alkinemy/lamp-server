package lamp.collector.common.service.metrics.collector;

import lamp.collector.common.domain.AppMetrics;
import lamp.collector.common.domain.WatchedApp;

public interface AppMetricsCollector {

	AppMetrics getMetrics(WatchedApp watchedApp);

}
