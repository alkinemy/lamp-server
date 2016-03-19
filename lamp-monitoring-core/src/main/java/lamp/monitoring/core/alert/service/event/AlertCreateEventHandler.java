package lamp.monitoring.core.alert.service.event;

import lamp.monitoring.core.alert.model.event.AlertCreateEvent;
import lamp.monitoring.core.alert.model.AlertState;
import lamp.monitoring.core.alert.service.AlertEventProducer;
import lamp.monitoring.core.alert.service.AlertService;

public class AlertCreateEventHandler {

    private AlertService alertService;

    private AlertEventProducer alertEventProducer;

    public void handle(AlertCreateEvent event) {
        AlertState state = event.getState();
        if (AlertState.ALERT.equals(state)) {
            // action
        } else if (AlertState.UNDETERMINED.equals(state)) {
            // action
        }
    }

}
