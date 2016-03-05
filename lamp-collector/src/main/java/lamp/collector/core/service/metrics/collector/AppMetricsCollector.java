package lamp.collector.core.service.metrics.collector;

import lamp.collector.core.domain.AppMetrics;
import lamp.collector.core.domain.TargetApp;

public interface AppMetricsCollector {

	AppMetrics getMetrics(TargetApp targetApp);

}
