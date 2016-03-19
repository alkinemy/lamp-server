package lamp.monitoring.core.health.service;

import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.alert.model.AlertRuleExpression;
import lamp.monitoring.core.alert.model.AlertState;
import lamp.monitoring.core.alert.model.event.AlertRuleExpressionEvaluationEvent;
import lamp.monitoring.core.alert.service.AlertEventProducer;
import lamp.common.collector.model.TargetHealth;
import lamp.common.utils.ExceptionUtils;

import java.util.Date;
import java.util.List;

public class SimpleHealthMonitoringProcessor extends HealthMonitoringProcessor {

	private HealthAlertRuleProvider healthAlertRuleProvider;
	private AlertEventProducer alertEventProducer;

	public SimpleHealthMonitoringProcessor(HealthAlertRuleProvider healthAlertRuleProvider, AlertEventProducer alertEventProducer) {
		this.healthAlertRuleProvider = healthAlertRuleProvider;
		this.alertEventProducer = alertEventProducer;
	}

	@Override
	protected void monitoring(TargetHealth targetHealth) {
		Date stateTime = new Date();
		List<AlertRule> alertRules = healthAlertRuleProvider.getHealthAlertRules();
		for (AlertRule alertRule : alertRules) {
			AlertRuleExpression expression = alertRule.getExpression();

			AlertRuleExpressionEvaluationEvent event = new AlertRuleExpressionEvaluationEvent();
			event.setTenantId(targetHealth.getId());
			event.setAlertRuleId(alertRule.getId());
			event.setAlertType(alertRule.getType());
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
