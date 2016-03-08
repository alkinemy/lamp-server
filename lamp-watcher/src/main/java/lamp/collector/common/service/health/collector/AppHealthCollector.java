package lamp.collector.common.service.health.collector;

import lamp.collector.common.domain.AppHealth;
import lamp.collector.common.domain.MonitoringTarget;

public interface AppHealthCollector {

	AppHealth getHealth(MonitoringTarget watchedApp);

}
