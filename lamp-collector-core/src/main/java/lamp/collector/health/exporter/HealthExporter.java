package lamp.collector.health.exporter;

import lamp.common.collector.service.HealthProcessor;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HealthExporter implements HealthProcessor {

	@Override
	public boolean canProcess(HealthTarget healthTarget) {
		return true;
	}

	@Override
	public void process(HealthTarget healthTarget, TargetHealth targetHealth, Throwable t) {
		if (targetHealth != null) {
			export(targetHealth);
		} else {
			log.warn("targetHealth is null : target={}, throwable={}", healthTarget, t);
		}
	}

	protected abstract void export(TargetHealth targetHealth);
}
