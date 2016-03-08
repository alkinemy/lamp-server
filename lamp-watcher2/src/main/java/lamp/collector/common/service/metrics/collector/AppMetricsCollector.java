package lamp.collector.common.service.metrics.collector;

import lamp.collector.common.domain.AppMetrics;
import lamp.collector.common.domain.MonitoringTarget;

public interface AppMetricsCollector {

	AppMetrics getMetrics(MonitoringTarget watchedApp);

}
