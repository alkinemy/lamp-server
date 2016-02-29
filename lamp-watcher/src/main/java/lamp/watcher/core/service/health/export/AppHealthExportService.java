package lamp.watcher.core.service.health.export;

import lamp.watcher.core.domain.AppHealth;
import lamp.watcher.core.domain.WatchedApp;

public interface AppHealthExportService {

	void export(WatchedApp watchedApp, AppHealth health);

}
