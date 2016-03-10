package lamp.common.metrics;

public interface HealthLoader {

	TargetHealth getHealth(HealthTarget healthTarget);

}
