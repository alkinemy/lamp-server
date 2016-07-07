package lamp.monitoring.core.alert;

import lamp.monitoring.core.alert.model.AlertRule;

import java.util.List;

public interface AlertRuleProvider<R extends AlertRule> {

	 List<R> getAlertRules();

}
