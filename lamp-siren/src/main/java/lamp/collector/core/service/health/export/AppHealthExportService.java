package lamp.collector.core.service.health.export;

import lamp.collector.core.domain.AppHealth;
import lamp.collector.core.domain.WatchedApp;

public interface AppHealthExportService {

	void export(WatchedApp watchedApp, AppHealth health);

}
