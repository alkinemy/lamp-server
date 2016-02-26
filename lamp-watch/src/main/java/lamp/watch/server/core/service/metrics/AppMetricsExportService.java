package lamp.watch.server.core.service.metrics;

import lamp.watch.server.core.domain.WatchedApp;

import java.util.Map;

public interface AppMetricsExportService {

	void exportMetrics(WatchedApp app, Map<String, Object> metrics, Map<String, String> tags);

}
