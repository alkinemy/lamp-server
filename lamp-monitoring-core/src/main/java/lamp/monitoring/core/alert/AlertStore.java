package lamp.monitoring.core.alert;

import lamp.monitoring.core.alert.model.Alert;
import lamp.monitoring.core.alert.model.AlertCondition;
import lamp.monitoring.core.alert.model.AlertEvent;

import java.util.EnumSet;
import java.util.Optional;

public interface AlertStore {

    Optional<Alert> getAlertOptional(String ruleId, String targetId, EnumSet<AlertCondition> conditions);

    Alert createAlert(Alert alert);

    void createAlertEventHistory(Alert alert, AlertEvent alertEvent);

    void updateAlertCondition(Alert alert, AlertCondition resolved);
}
