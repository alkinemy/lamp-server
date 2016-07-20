package lamp.collector.core.metrics.processor;

import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.handler.TargetMetricsAsyncExecutor;
import lamp.collector.core.metrics.handler.TargetMetricsHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TargetMetricsAsyncProcessor<T extends TargetMetrics> extends TargetMetricsProcessor<T> {

	private final TargetMetricsAsyncExecutor targetMetricsAsyncExecutor;

	public TargetMetricsAsyncProcessor(List<TargetMetricsHandler<T>> targetMetricsHandlers, TargetMetricsAsyncExecutor targetMetricsAsyncExecutor) {
		super(targetMetricsHandlers);
		this.targetMetricsAsyncExecutor = targetMetricsAsyncExecutor;
	}

	@Override
	protected void doProcess(T targetMetrics, TargetMetricsHandler<T> handler) {
		targetMetricsAsyncExecutor.execute(handler, targetMetrics, (target, exception) -> doProcessException(targetMetrics, "TargetMetricsAsync execute Failed", exception));
	}

}
