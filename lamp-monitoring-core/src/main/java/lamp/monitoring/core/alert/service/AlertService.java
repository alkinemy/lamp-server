package lamp.monitoring.core.alert.service;

import lamp.common.monitoring.model.Tenant;
import lamp.monitoring.core.alert.model.Alert;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.alert.model.AlertStateHistory;

import java.util.Optional;

public interface AlertService {

    Optional<Alert> getAlertOptional(Tenant target, AlertRule<?> alertRule);

    Alert getAlert(String alertId);

    Optional<Alert> getAlertOptional(String alertId);

    Alert createOrGetAlert(AlertStateHistory expressionEvaluationEvent);

    void updateState(Alert a, AlertStateHistory stateHistory);



}
