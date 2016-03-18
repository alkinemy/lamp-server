package lamp.alert.core.service;

import lamp.alert.core.model.AlertEvent;

public interface AlertEventProducer {

	void send(AlertEvent alertEvent);

}
