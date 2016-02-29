package lamp.watcher.core.service.metrics.export;

import lamp.watcher.core.domain.AppMetrics;
import lamp.watcher.core.domain.WatchedApp;

public interface AppMetricsExportService {

	void export(WatchedApp app, AppMetrics metrics);

}