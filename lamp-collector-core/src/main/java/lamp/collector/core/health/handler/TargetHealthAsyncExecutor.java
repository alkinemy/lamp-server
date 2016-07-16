package lamp.collector.core.health.handler;

import lamp.collector.core.metrics.handler.AsyncExecuteCallback;
import lamp.collector.core.health.TargetHealth;

public interface TargetHealthAsyncExecutor {

	<T extends TargetHealth> void execute(TargetHealthHandler<T> handler, T targetMetrics, AsyncExecuteCallback<T> callback);

}
