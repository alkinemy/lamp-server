package lamp.collector.core.health.loader.http;


import lamp.collector.core.health.HealthStatus;
import lamp.collector.core.health.HealthTarget;
import lamp.collector.core.health.TargetHealth;
import lamp.collector.core.health.loader.TargetHealthLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TargetHealthHttpLoader extends HealthHttpLoader implements TargetHealthLoader {

	@Override
	public TargetHealth getHealth(HealthTarget metricsTarget) {
		long timestamp = System.currentTimeMillis();
		try {
			HealthStatus health = getHealth(metricsTarget.getEndpoint());

			return new TargetHealth(metricsTarget, timestamp, health);
		} catch (Exception e) {
			return new TargetHealth(metricsTarget, timestamp, e);
		}
	}

}
