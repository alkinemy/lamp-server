package lamp.monitoring.core.alert.service;

import lamp.monitoring.core.alert.model.Alert;
import lamp.monitoring.core.alert.model.AlertStateHistory;

import java.util.Optional;

public interface AlertService {

    Alert createOrGetAlert(AlertStateHistory expressionEvaluationEvent);

    void updateState(Alert a, AlertStateHistory stateHistory);

    Optional<Alert> getAlertOptional(String alertRuleId, String tenantId);
}
