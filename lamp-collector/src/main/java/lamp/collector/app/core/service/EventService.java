package lamp.collector.app.core.service;

import lamp.event.common.Event;
import lamp.event.common.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventService implements EventPublisher {

	public void publish(Event event) {
		log.info("event = {}", event);
	}

}
