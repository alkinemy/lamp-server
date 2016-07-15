package lamp.collector.core.metrics.processor;

import lamp.collector.core.metrics.MetricsTarget;
import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.loader.TargetMetricsLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetricsTargetProcessor<T extends MetricsTarget, M extends TargetMetrics> {

	private final TargetMetricsLoader<T, M> targetMetricsLoader;
	private final TargetMetricsProcessor<M> targetMetricsProcessor;

	public MetricsTargetProcessor(TargetMetricsLoader<T, M> targetMetricsLoader, TargetMetricsProcessor<M> targetMetricsProcessor) {
		this.targetMetricsLoader = targetMetricsLoader;
		this.targetMetricsProcessor = targetMetricsProcessor;
	}

	public void process(T target) {
		M targetMetrics = targetMetricsLoader.getMetrics(target);
		targetMetricsProcessor.process(targetMetrics);
	}

}
