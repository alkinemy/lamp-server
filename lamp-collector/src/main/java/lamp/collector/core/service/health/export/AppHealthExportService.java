package lamp.collector.core.service.health.export;

import lamp.collector.core.domain.AppHealth;
import lamp.collector.core.domain.TargetApp;

public interface AppHealthExportService {

	void export(TargetApp targetApp, AppHealth health);

}
