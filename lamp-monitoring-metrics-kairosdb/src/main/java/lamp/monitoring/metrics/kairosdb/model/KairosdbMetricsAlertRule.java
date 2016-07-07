package lamp.monitoring.metrics.kairosdb.model;

import lamp.monitoring.core.alert.model.AbstractAlertRule;

public class KairosdbMetricsAlertRule extends AbstractAlertRule<Object, KairosdbAlertRuleExpression> {
	@Override public boolean isAlertTarget(Object target) {
		return false;
	}
}
