package lamp.collector.core.metrics.handler;

import lamp.collector.core.metrics.TargetMetrics;

public interface TargetMetricsAsyncExecutor {

	<T extends TargetMetrics> void execute(TargetMetricsHandler<T> handler, T targetMetrics, AsyncExecuteCallback<T> callback);

}
