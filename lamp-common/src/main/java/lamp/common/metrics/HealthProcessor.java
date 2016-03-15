package lamp.common.metrics;

public interface HealthProcessor {

	void process(HealthTarget healthTarget, TargetHealth targetHealth, Throwable t);

}
