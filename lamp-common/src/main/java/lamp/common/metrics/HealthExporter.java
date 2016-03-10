package lamp.common.metrics;

public interface HealthExporter {

	void export(TargetHealth targetHealth);

}
