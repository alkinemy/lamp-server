package lamp.admin.domain.monitoring.service;

import lamp.monitoring.core.alert.AlertStore;
import lamp.monitoring.core.alert.model.Alert;
import lamp.monitoring.core.alert.model.AlertCondition;
import lamp.monitoring.core.alert.model.AlertEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Service
public class AlertCommandService implements AlertStore {

	private List<Alert> alertList = new ArrayList<>();

	@Override public Optional<Alert> getAlertOptional(String ruleId, String targetId, EnumSet<AlertCondition> conditions) {
		return alertList.stream().filter(a -> a.getRuleId().equals(ruleId) && a.getTargetId().equals(targetId)).findFirst();
	}

	@Override public Alert createAlert(Alert alert) {
		alertList.add(alert);
		return alert;
	}

	@Override public void createAlertEventHistory(Alert alert, AlertEvent alertEvent) {

	}

	@Override public void updateAlertCondition(Alert alert, AlertCondition alertCondition) {
		alert.setCondition(alertCondition);
	}
}
