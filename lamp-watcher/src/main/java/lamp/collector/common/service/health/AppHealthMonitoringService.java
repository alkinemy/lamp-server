package lamp.collector.common.service.health;

import lamp.common.utils.BooleanUtils;
import lamp.collector.common.domain.AppHealth;
import lamp.collector.common.domain.MonitoringTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppHealthMonitoringService {

	public void monitoring(MonitoringTarget watchedApp, AppHealth health) {
		if (!BooleanUtils.isTrue(watchedApp.getHealthMonitoringEnabled())) {
			return;
		}
		log.info("monitoring : app = {}, health = {}", watchedApp, health);
	}

}
