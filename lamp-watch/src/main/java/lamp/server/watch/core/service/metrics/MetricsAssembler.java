package lamp.server.watch.core.service.metrics;

import lamp.server.watch.core.domain.WatchedApp;
import lamp.server.watch.core.domain.WatchedAppMetrics;

import java.util.Map;

public interface MetricsAssembler {

	WatchedAppMetrics assemble(long timestamp, WatchedApp watchedApp, Map<String, Object> metrics);

}
