package lamp.server.watch.core.service.metrics;

import lamp.server.watch.core.domain.WatchedApp;

import java.util.Map;

public interface MetricsAssembler {

	Map<String,Object> assemble(WatchedApp watchedApp, Map<String, Object> metrics);

}
