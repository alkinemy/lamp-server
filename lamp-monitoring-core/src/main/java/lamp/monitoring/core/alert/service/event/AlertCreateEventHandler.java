package lamp.monitoring.core.alert.service.event;

import lamp.common.utils.CollectionUtils;
import lamp.monitoring.core.alert.model.Alert;
import lamp.monitoring.core.alert.model.AlertActionContext;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.alert.model.AlertState;
import lamp.monitoring.core.alert.model.event.AlertCreateEvent;
import lamp.monitoring.core.alert.service.AlertActionService;

import java.util.List;

public class AlertCreateEventHandler {

    private AlertActionService alertActionService;

    public void handle(AlertCreateEvent event) {
        Alert alert = event.getAlert();
        AlertState state = alert.getNewState();
        AlertRule<?> alertRule = alert.getRule();

        List<String> actions = null;
        if (AlertState.ALERT.equals(state)) {
            actions = alertRule.getAlertActions();
        } else if (AlertState.UNDETERMINED.equals(state)) {
            actions = alertRule.getUndeterminedActions();
        }

        if (CollectionUtils.isNotEmpty(actions)) {
            AlertActionContext context = new AlertActionContext();
            alertActionService.doActions(context, actions);
        }
    }

}
