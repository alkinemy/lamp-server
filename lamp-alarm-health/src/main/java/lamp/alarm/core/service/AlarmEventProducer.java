package lamp.alarm.core.service;

import lamp.alarm.core.model.AlarmEvent;

public interface AlarmEventProducer {

	void send(AlarmEvent alarmEvent);

}
