package lamp.watcher.core.service;

import lamp.common.collection.health.TargetHealth;
import lamp.common.monitoring.MonitoringTarget;
import lamp.common.utils.BooleanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MonitoringHealthService {

	public void monitoring(MonitoringTarget monitoringTarget, TargetHealth targetHealth) {
		if (BooleanUtils.isTrue(monitoringTarget.getHealthCollectionEnabled())) {

		}
	}


}
