package lamp.monitoring.core.alert.service.event;

import lamp.monitoring.core.alert.model.*;
import lamp.monitoring.core.alert.model.event.AlertCreateEvent;
import lamp.monitoring.core.alert.model.event.AlertRuleExpressionEvaluationEvent;
import lamp.monitoring.core.alert.model.event.AlertStateChangeEvent;
import lamp.monitoring.core.alert.service.AlertEventProducer;
import lamp.monitoring.core.alert.service.AlertService;

import java.util.Optional;

public class AlertRuleExpressionEvaluationEventHandler {

    private AlertService alertService;

    private AlertEventProducer alertEventProducer;

    public void handle(AlertRuleExpressionEvaluationEvent event) {
        AlertState state = event.getState();
        if (AlertState.OK.equals(state)) {
            Optional<Alert> alertOptional = alertService.getAlertOptional(event.getTenant(), event.getAlertRule());
            alertOptional.ifPresent(alert -> {
                AlertStateHistory stateHistory = newAlertStateHistory(event);
                alertService.updateState(alert, stateHistory);

                // Alert State Change Event
                AlertStateChangeEvent stateChangeEvent = new AlertStateChangeEvent();
                stateChangeEvent.setAlert(alert);
                alertEventProducer.send(stateChangeEvent);
            });
        } else {
            AlertStateHistory stateHistory = newAlertStateHistory(event);
            Alert alert = alertService.createOrGetAlert(stateHistory);
            if (alert.getOldState() == null) {
                 // Alert Create event
                AlertCreateEvent createEvent = new AlertCreateEvent();
                createEvent.setAlert(alert);
                alertEventProducer.send(createEvent);
            } else {
                alertService.updateState(alert, stateHistory);
                if (!alert.getOldState().equals(alert.getNewState())) {
                    // Alert State Change Event
                    AlertStateChangeEvent stateChangeEvent = new AlertStateChangeEvent();
                    stateChangeEvent.setAlert(alert);

                    alertEventProducer.send(stateChangeEvent);
                }
            }
        }


    }

    protected AlertStateHistory newAlertStateHistory(AlertRuleExpressionEvaluationEvent event) {
        return null;
    }
}
