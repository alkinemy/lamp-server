package lamp.monitoring.metrics.kairosdb.model;

import lamp.monitoring.core.alert.model.AlertRule;

public class KairosdbMetricsAlertRule extends AlertRule {
	@Override public boolean isAlertTarget(Object target) {
		return false;
	}
}
