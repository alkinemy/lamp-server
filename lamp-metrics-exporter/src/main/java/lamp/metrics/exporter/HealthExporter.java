package lamp.metrics.exporter;

import lamp.common.metrics.HealthProcessor;
import lamp.common.metrics.HealthTarget;
import lamp.common.metrics.TargetHealth;

public abstract class HealthExporter implements HealthProcessor {

	@Override
	public void process(HealthTarget healthTarget, TargetHealth targetHealth, Throwable t) {
		if (targetHealth != null) {
			export(targetHealth);
		}
	}

	protected abstract void export(TargetHealth targetHealth);
}
