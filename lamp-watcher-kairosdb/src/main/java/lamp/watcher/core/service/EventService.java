package lamp.watcher.core.service;

import lamp.common.event.Event;
import lamp.common.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventService implements EventPublisher {

	public void publish(Event event) {
		log.info("event = {}", event);
	}

}
