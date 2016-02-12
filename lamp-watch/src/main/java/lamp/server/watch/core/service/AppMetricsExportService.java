package lamp.server.watch.core.service;

import lamp.server.watch.core.domain.WatchedAppMetrics;


public interface AppMetricsExportService {

	void exportMetrics(WatchedAppMetrics watchedAppMetrics);

}