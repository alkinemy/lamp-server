package lamp.watch.server.core.service;

import lamp.watch.server.core.domain.WatchedAppMetrics;


public interface AppMetricsExportService {

	void exportMetrics(WatchedAppMetrics watchedAppMetrics);

}