package lamp.watcher.core.service.health;

import lamp.admin.utils.BooleanUtils;
import lamp.watcher.core.domain.AppHealth;
import lamp.watcher.core.domain.WatchedApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppHealthMonitoringService {

	public void monitoring(WatchedApp watchedApp, AppHealth health) {
		if (!BooleanUtils.isTrue(watchedApp.getHealthMonitoringEnabled())) {
			return;
		}
		log.info("monitoring : app = {}, health = {}", watchedApp, health);
	}

}
