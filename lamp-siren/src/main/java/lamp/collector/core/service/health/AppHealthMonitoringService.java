package lamp.collector.core.service.health;

import lamp.admin.utils.BooleanUtils;
import lamp.collector.core.domain.AppHealth;
import lamp.collector.core.domain.WatchedApp;
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
