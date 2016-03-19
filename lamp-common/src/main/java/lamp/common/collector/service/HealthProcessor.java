package lamp.common.collector.service;

import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;

public interface HealthProcessor {

	boolean canProcess(HealthTarget healthTarget);

	void process(HealthTarget healthTarget, TargetHealth targetHealth, Throwable t);

}
