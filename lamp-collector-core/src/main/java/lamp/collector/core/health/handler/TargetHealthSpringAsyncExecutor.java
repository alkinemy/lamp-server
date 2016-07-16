package lamp.collector.core.health.handler;

import lamp.collector.core.health.TargetHealth;
import lamp.collector.core.metrics.handler.AsyncExecuteCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class TargetHealthSpringAsyncExecutor implements TargetHealthAsyncExecutor {

	@Async("targetHealthAsyncThreadPoolTaskExecutor")
	public <T extends TargetHealth> void execute(TargetHealthHandler<T> handler, T targetHealth, AsyncExecuteCallback<T> callback) {
		try {
			handler.handle(targetHealth);
			if (callback != null) {

			}
			callback.onCompletion(targetHealth, null);
		} catch(Exception e) {
			if (callback != null) {

			}
			callback.onCompletion(targetHealth, e);
		}
	}

}
