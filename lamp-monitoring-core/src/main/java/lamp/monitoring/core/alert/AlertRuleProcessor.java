package lamp.monitoring.core.alert;

import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertRule;

public interface AlertRuleProcessor<T, R extends AlertRule> {

	boolean canProcess(AlertRule alertRule);

	AlertEvent doProcess(T target, R rule);

}
