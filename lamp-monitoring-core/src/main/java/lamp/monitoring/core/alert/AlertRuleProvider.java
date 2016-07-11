package lamp.monitoring.core.alert;

import lamp.monitoring.core.alert.model.AlertRule;

import java.util.List;

public interface AlertRuleProvider {

	 List<? extends AlertRule> getAlertRules();

}
