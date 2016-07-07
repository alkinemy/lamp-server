package lamp.monitoring.core.alert;

public interface MonitoringProcessor<T> {

	void monitoring(T target);

}
