package lamp.alarm.health.model;

import lamp.alarm.core.model.AlarmExpression;
import lamp.alarm.core.model.AlarmState;
import lamp.common.metrics.HealthConstants;
import lamp.common.metrics.HealthStatusCode;
import lamp.common.metrics.TargetHealth;

import java.util.Map;

public class HealthAlarmExpression implements AlarmExpression<TargetHealth> {

	@Override public AlarmState getState(TargetHealth target) {
		Map<String, Object> health = target.getHealth();
		String status = health != null ? (String) health.get(HealthConstants.STATUS) : null;

		return HealthStatusCode.UP.name().equals(status) ? AlarmState.OK : AlarmState.ALARM;
	}

}
