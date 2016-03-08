package lamp.collector.common.service.metrics.export;

import lamp.collector.common.domain.AppMetrics;
import lamp.collector.common.domain.WatchedApp;

public interface AppMetricsExportService {

	void export(WatchedApp app, AppMetrics metrics);

}