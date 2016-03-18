package lamp.alert.health.model;

import lamp.alert.core.model.AlertRuleExpression;
import lamp.alert.core.model.AlertState;
import lamp.common.metrics.HealthConstants;
import lamp.common.metrics.HealthStatusCode;
import lamp.common.metrics.TargetHealth;

import java.util.Map;

public class HealthAlertRuleExpression implements AlertRuleExpression<TargetHealth> {

	@Override public AlertState evaluate(TargetHealth context) {
		Map<String, Object> health = context.getHealth();
		String status = health != null ? (String) health.get(HealthConstants.STATUS) : null;

		return HealthStatusCode.UP.name().equals(status) ? AlertState.OK : AlertState.ALERT;
	}

}
