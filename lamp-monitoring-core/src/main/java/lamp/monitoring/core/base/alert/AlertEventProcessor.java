package lamp.monitoring.core.base.alert;

import lamp.common.utils.CollectionUtils;
import lamp.monitoring.core.MonitoringTarget;
import lamp.monitoring.core.base.alert.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class AlertEventProcessor {

    private AlertStore alertStore;

    private AlertActionsExecutor alertActionsExecutor;

    public AlertEventProcessor(AlertStore alertStore, AlertActionsExecutor alertActionsExecutor) {
        this.alertStore = alertStore;
        this.alertActionsExecutor = alertActionsExecutor;
    }

    public void process(AlertEvent event) {
        AlertState state = event.getState();
        AlertStateCode stateCode = state.getCode();
        AlertRule rule = event.getRule();
        MonitoringTarget target = event.getTarget();

        Alert alert = null;

        List<String> actionIds = null;
        if (AlertStateCode.OK.equals(stateCode)) {
            Optional<Alert> alertOptional = getAlertOptional(rule.getId(), target.getId(), EnumSet.of(AlertCondition.NEW, AlertCondition.ACKNOWLEDGED));
            alert = alertOptional.orElse(null);
            if (alert != null) {
                alertStore.updateAlertCondition(alert, AlertCondition.RESOLVED);
                actionIds = rule.getOkActions();
            }
        } else if (AlertStateCode.ALERT.equals(stateCode)) {
            Optional<Alert> alertOptional = getAlertOptional(rule.getId(), target.getId(),
                                                             EnumSet.of(AlertCondition.NEW));
            alert = alertOptional.orElse(createAlert(event));
            actionIds = rule.getAlertActions();
        } else if (AlertStateCode.UNDETERMINED.equals(stateCode)) {
            Optional<Alert> alertOptional = getAlertOptional(rule.getId(), target.getId(),
                                                             EnumSet.of(AlertCondition.NEW));
            alert = alertOptional.orElse(createAlert(event));
            actionIds = rule.getUndeterminedActions();
        }

        log.debug("alert = {}, actionIds = {}", alert, actionIds);
        if (alert != null && CollectionUtils.isNotEmpty(actionIds)) {
            alert.setLastEventTime(event.getTimestamp());

            AlertActionContext context = new AlertActionContext();
            context.setAlert(alert);
            context.setAlertEvent(event);
            try {
                alertActionsExecutor.doActions(context, actionIds);
            } catch (Exception e) {
                log.error("Alert action failed", e);
            }
            alertStore.createAlertEventHistory(alert, event);
        }
    }

    protected Alert createAlert(AlertEvent event) {
        Alert alert = new Alert();
        alert.setId(UUID.randomUUID().toString());
        alert.setRuleId(event.getRule().getId());
        alert.setTargetId(event.getTarget().getId());
        alert.setCondition(AlertCondition.NEW);
        alert.setFirstEventTime(event.getTimestamp());

        return alertStore.createAlert(alert);
    }

    protected Optional<Alert> getAlertOptional(String ruleId, String targetId, EnumSet<AlertCondition> conditions) {
        return alertStore.getAlertOptional(ruleId, targetId, conditions);
    }

}
