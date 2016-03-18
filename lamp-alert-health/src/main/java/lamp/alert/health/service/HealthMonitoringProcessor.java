package lamp.alert.health.service;

import lamp.alert.core.model.AlertRule;
import lamp.alert.core.model.AlertRuleExpression;
import lamp.alert.core.model.AlertState;
import lamp.alert.core.model.ExpressionEvaluationEvent;
import lamp.alert.core.service.AlertEventProducer;
import lamp.common.metrics.*;
import lamp.common.utils.ExceptionUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HealthMonitoringProcessor implements HealthProcessor {

	private HealthAlertRuleProvider healthAlertRuleProvider;
	private AlertEventProducer alertEventProducer;

	public HealthMonitoringProcessor(HealthAlertRuleProvider healthAlertRuleProvider, AlertEventProducer alertEventProducer) {
		this.healthAlertRuleProvider = healthAlertRuleProvider;
		this.alertEventProducer = alertEventProducer;
	}

	@Override
	public void process(HealthTarget healthTarget, TargetHealth targetHealth, Throwable t) {
		if (targetHealth == null) {
			targetHealth = new TargetHealth();
			targetHealth.setId(healthTarget.getId());
			targetHealth.setName(healthTarget.getName());

			Map<String, Object> health = new LinkedHashMap<>();
			health.put(HealthConstants.STATUS, HealthStatusCode.UNKNOWN.name());
			if (t != null) {
				health.put(HealthConstants.DESCRIPTION, ExceptionUtils.getStackTrace(t));
			}
			targetHealth.setHealth(health);
		}

		monitoring(targetHealth);
	}

	protected void monitoring(TargetHealth targetHealth) {
		Date stateTime = new Date();
		List<AlertRule> alertRules = healthAlertRuleProvider.getHealthAlertRules();
		for (AlertRule alertRule : alertRules) {
			AlertRuleExpression expression = alertRule.getExpression();

			ExpressionEvaluationEvent event = new ExpressionEvaluationEvent();
			event.setTenantId(targetHealth.getId());
			event.setAlarmDefinitionId(alertRule.getId());
			event.setAlarmType(alertRule.getType());
			event.setSeverity(alertRule.getSeverity());
			event.setDimension(targetHealth.getHealth());
			event.setStateTime(stateTime);
			try {
				AlertState state = expression.evaluate(targetHealth);
				event.setState(state);
			} catch (Throwable t) {
				event.setState(AlertState.UNDETERMINED);
				event.setStateDescription(ExceptionUtils.getStackTrace(t));
			}

			alertEventProducer.send(event);
		}
	}

}
