package lamp.collector.common.health;

import lamp.collector.common.TargetHealth;

public interface HealthExporter {

	void export(TargetHealth targetHealth);

}
