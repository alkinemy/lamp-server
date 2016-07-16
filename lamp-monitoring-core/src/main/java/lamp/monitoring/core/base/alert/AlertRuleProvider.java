package lamp.monitoring.core.base.alert;

import lamp.monitoring.core.base.alert.model.AlertRule;

import java.util.List;

public interface AlertRuleProvider {

	 List<? extends AlertRule> getAlertRules();

}
