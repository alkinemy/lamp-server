package lamp.collector.core.health.loader;


import lamp.collector.core.health.HealthTarget;
import lamp.collector.core.health.TargetHealth;

public interface TargetHealthLoader<T extends HealthTarget,  M extends TargetHealth>  {

	M getHealth(T target);

}
