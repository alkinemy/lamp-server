package lamp.collector.core.health.processor;


import lamp.collector.core.health.TargetHealth;
import lamp.collector.core.health.handler.TargetHealthAsyncExecutor;
import lamp.collector.core.health.handler.TargetHealthHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TargetHealthAsyncProcessor<T extends TargetHealth> extends TargetHealthProcessor<T> {

	private final TargetHealthAsyncExecutor targetHealthAsyncExecutor;

	public TargetHealthAsyncProcessor(List<TargetHealthHandler<T>> targetHealthHandlers, TargetHealthAsyncExecutor targetHealthAsyncExecutor) {
		super(targetHealthHandlers);
		this.targetHealthAsyncExecutor = targetHealthAsyncExecutor;
	}

	@Override
	protected void doProcess(T targetHealth, TargetHealthHandler<T> handler) {
		targetHealthAsyncExecutor.execute(handler, targetHealth, (target, exception) -> doProcessException(targetHealth, "TargetHealth Async execute Failed", exception));
	}

}
