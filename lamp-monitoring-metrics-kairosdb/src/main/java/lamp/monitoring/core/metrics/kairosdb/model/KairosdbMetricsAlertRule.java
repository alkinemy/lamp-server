package lamp.monitoring.core.metrics.kairosdb.model;

import lamp.monitoring.core.base.alert.model.AlertRule;

public class KairosdbMetricsAlertRule extends AlertRule {
	@Override public boolean isAlertTarget(Object target) {
		return false;
	}
}
