package lamp.collector.core.health.handler;


import lamp.collector.core.health.TargetHealth;

public interface TargetHealthHandler<T extends TargetHealth> {

	void handle(T targetHealth);

}