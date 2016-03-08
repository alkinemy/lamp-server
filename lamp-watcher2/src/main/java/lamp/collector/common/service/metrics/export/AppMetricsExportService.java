package lamp.collector.common.service.metrics.export;

import lamp.collector.common.domain.AppMetrics;
import lamp.collector.common.domain.MonitoringTarget;

public interface AppMetricsExportService {

	void export(MonitoringTarget app, AppMetrics metrics);

}