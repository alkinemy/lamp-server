package lamp.admin.web.service;

import lamp.alarm.core.model.AlarmEvent;
import lamp.alarm.core.service.AlarmEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlarmEventService implements AlarmEventProducer {

	@Override public void send(AlarmEvent alarmEvent) {
		log.info("alarmEvent = {}", alarmEvent);
	}

}
