package lamp.admin.web.service;


import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.service.AlertEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlertEventService implements AlertEventProducer {

	@Override public void send(AlertEvent alertEvent) {
		log.info("alarmEvent = {}", alertEvent);
	}

}
