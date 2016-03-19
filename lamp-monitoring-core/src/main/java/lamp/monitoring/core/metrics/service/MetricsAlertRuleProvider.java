package lamp.monitoring.core.metrics.service;

import lamp.monitoring.core.alert.model.AlertRule;

import java.util.List;

public interface MetricsAlertRuleProvider {

	<R extends AlertRule> List<R> getMetricsAlertRules(Class<R> ruleClass);

}
