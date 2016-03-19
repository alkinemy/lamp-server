package lamp.monitoring.core.health.model;

import lamp.monitoring.core.alert.model.AlertRuleExpression;
import lamp.monitoring.core.alert.model.AlertState;
import lamp.common.collector.model.HealthConstants;
import lamp.common.collector.model.HealthStatusCode;
import lamp.common.collector.model.TargetHealth;

import java.util.Map;

public class HealthAlertRuleExpression implements AlertRuleExpression<TargetHealth> {

	@Override public AlertState evaluate(TargetHealth context) {
		Map<String, Object> health = context.getHealth();
		String status = health != null ? (String) health.get(HealthConstants.STATUS) : null;

		return HealthStatusCode.UP.name().equals(status) ? AlertState.OK : AlertState.ALERT;
	}

}
