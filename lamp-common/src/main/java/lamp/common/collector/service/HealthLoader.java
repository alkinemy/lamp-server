package lamp.common.collector.service;

import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;

public interface HealthLoader {

	TargetHealth getHealth(HealthTarget healthTarget);

}
