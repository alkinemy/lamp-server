package lamp.monitoring.core.base.alert;

import lamp.monitoring.core.base.alert.model.AlertEvent;

public interface AlertEventProducer {

	void send(AlertEvent alertEvent);

}
