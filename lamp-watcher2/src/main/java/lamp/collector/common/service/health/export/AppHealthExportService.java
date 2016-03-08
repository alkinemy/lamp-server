package lamp.collector.common.service.health.export;

import lamp.collector.common.domain.AppHealth;
import lamp.collector.common.domain.MonitoringTarget;

public interface AppHealthExportService {

	void export(MonitoringTarget watchedApp, AppHealth health);

}
