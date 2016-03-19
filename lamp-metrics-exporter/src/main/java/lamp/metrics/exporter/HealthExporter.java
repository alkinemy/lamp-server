package lamp.metrics.exporter;

import lamp.common.collector.service.HealthProcessor;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;

public abstract class HealthExporter implements HealthProcessor {

	@Override
	public boolean canProcess(HealthTarget healthTarget) {
		return true;
	}

	@Override
	public void process(HealthTarget healthTarget, TargetHealth targetHealth, Throwable t) {
		if (targetHealth != null) {
			export(targetHealth);
		}
	}

	protected abstract void export(TargetHealth targetHealth);
}
