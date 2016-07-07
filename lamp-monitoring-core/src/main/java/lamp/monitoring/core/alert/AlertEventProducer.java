package lamp.monitoring.core.alert;

import lamp.monitoring.core.alert.model.AlertEvent;

public interface AlertEventProducer {

	void send(AlertEvent alertEvent);

}
