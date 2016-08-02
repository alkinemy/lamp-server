package lamp.admin.web.base.service;

import lamp.common.event.Event;
import lamp.common.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.EventBus;

@Slf4j
@Service
public class EventService implements EventPublisher {

    @Autowired
    private EventBus eventBus;

    @Override public void publish(Event event) {
        log.info("event = {}", event);
        eventBus.notify(event.getName(), reactor.bus.Event.wrap(event));
    }

}
