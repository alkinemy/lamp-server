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
            Optional<Alert> alertOptional = alertService.getAlertOptional(event.getAlertRuleId(), event.getTenantId());
            alertOptional.ifPresent(alert -> {
                AlertStateHistory stateHistory = newAlertStateHistory(event);
                alertService.updateState(alert, stateHistory);

                // Alert State Change Event
                AlertStateChangeEvent stateChangeEvent = new AlertStateChangeEvent();
                alertEventProducer.send(stateChangeEvent);
            });
        } else {
            AlertStateHistory stateHistory = newAlertStateHistory(event);
            Alert alert = alertService.createOrGetAlert(stateHistory);
            AlertStateHistory lastStateHistory = alert.getLastStateHistory();
            if (stateHistory.getId().equals(lastStateHistory.getId())) {
                 // Alert Create event
                AlertCreateEvent createEvent = new AlertCreateEvent();
                alertEventProducer.send(createEvent);
            } else {
                alertService.updateState(alert, stateHistory);
                if (!lastStateHistory.getState().equals(stateHistory.getState())) {
                    // Alert State Change Event
                    AlertStateChangeEvent stateChangeEvent = new AlertStateChangeEvent();
                    alertEventProducer.send(stateChangeEvent);
                }
            }
        }


    }

    protected AlertStateHistory newAlertStateHistory(AlertRuleExpressionEvaluationEvent event) {
        return null;
    }
}
