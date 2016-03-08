package lamp.collector.common.service.health.export;

import lamp.collector.common.domain.AppHealth;
import lamp.collector.common.domain.WatchedApp;

public interface AppHealthExportService {

	void export(WatchedApp watchedApp, AppHealth health);

}
