package lamp.collector.core.service.metrics.export;

import lamp.collector.core.domain.AppMetrics;
import lamp.collector.core.domain.WatchedApp;

public interface AppMetricsExportService {

	void export(WatchedApp app, AppMetrics metrics);

}