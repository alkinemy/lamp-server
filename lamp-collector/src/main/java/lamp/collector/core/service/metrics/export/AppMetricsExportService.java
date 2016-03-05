package lamp.collector.core.service.metrics.export;

import lamp.collector.core.domain.AppMetrics;
import lamp.collector.core.domain.TargetApp;

public interface AppMetricsExportService {

	void export(TargetApp app, AppMetrics metrics);

}