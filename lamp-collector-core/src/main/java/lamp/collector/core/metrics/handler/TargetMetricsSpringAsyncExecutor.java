package lamp.collector.core.metrics.handler;

import lamp.collector.core.metrics.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class TargetMetricsSpringAsyncExecutor implements TargetMetricsAsyncExecutor {

	@Async("targetMetricsAsyncThreadPoolTaskExecutor")
	public <T extends TargetMetrics> void execute(TargetMetricsHandler<T> handler, T targetMetrics, AsyncExecuteCallback<T> callback) {
		try {
			handler.handle(targetMetrics);
			if (callback != null) {

			}
			callback.onCompletion(targetMetrics, null);
		} catch(Exception e) {
			if (callback != null) {

			}
			callback.onCompletion(targetMetrics, e);
		}
	}

}
