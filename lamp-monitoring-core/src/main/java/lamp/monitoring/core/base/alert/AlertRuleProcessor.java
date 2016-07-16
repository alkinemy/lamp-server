package lamp.monitoring.core.base.alert;

import lamp.monitoring.core.base.alert.model.AlertEvent;
import lamp.monitoring.core.base.alert.model.AlertRule;

public interface AlertRuleProcessor<T, R extends AlertRule> {

	boolean canProcess(AlertRule alertRule);

	AlertEvent doProcess(T target, R rule);

}
