package lamp.monitoring.core.health.service;

import lamp.monitoring.core.alert.AlertEventProducer;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.common.collector.model.TargetHealth;

import java.util.Date;
import java.util.List;

@Deprecated
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
//			AlertRuleExpression expression = alertRule.getRuleExpression();
//
//			AlertRuleMatchedEvent event = new AlertRuleMatchedEvent();
////			event.setTenant(targetHealth);
//			event.setAlertRule(alertRule);
//			event.setReasonData(targetHealth.getHealth());
//			event.setTimestamp(stateTime);
//			try {
//				AlertState state = expression.evaluate(targetHealth);
//				event.setState(state.getType());
//			} catch (Throwable t) {
//				event.setState(AlertStateType.UNDETERMINED);
//				event.setReason(ExceptionUtils.getStackTrace(t));
//			}
//
//			alertEventProducer.send(event);
		}
	}

}
