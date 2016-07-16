package lamp.collector.core.health.processor;


import lamp.collector.core.health.HealthTarget;
import lamp.collector.core.health.TargetHealth;
import lamp.collector.core.health.loader.TargetHealthLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HealthTargetProcessor<T extends HealthTarget, M extends TargetHealth> {

	private final TargetHealthLoader<T, M> targetHealthLoader;
	private final TargetHealthProcessor<M> targetHealthProcessor;

	public HealthTargetProcessor(TargetHealthLoader<T, M> targetHealthLoader, TargetHealthProcessor<M> targetHealthProcessor) {
		this.targetHealthLoader = targetHealthLoader;
		this.targetHealthProcessor = targetHealthProcessor;
	}

	public void process(T target) {
		M targetHealth = targetHealthLoader.getHealth(target);
		targetHealthProcessor.process(targetHealth);
	}

}
