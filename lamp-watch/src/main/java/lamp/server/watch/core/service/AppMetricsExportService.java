package lamp.server.watch.core.service;

import lamp.server.watch.core.domain.WatchedApp;

import java.util.Map;


public interface AppMetricsExportService {

	void exportMetrics(WatchedApp app, Map<String, Object> metrics, Map<String, String> tags);

}