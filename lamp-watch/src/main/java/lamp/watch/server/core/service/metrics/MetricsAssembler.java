package lamp.watch.server.core.service.metrics;

import lamp.watch.server.core.domain.WatchedApp;
import lamp.watch.server.core.domain.WatchedAppMetrics;

import java.util.Map;

public interface MetricsAssembler {

	WatchedAppMetrics assemble(long timestamp, WatchedApp watchedApp, Map<String, Object> metrics);

}
