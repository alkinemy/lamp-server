package lamp.alarm.metrics.service;

import lamp.alert.core.model.AlertRule;
import lamp.alert.core.model.AlertEvent;
import lamp.alert.core.model.AlertRuleExpression;
import lamp.alert.core.model.AlertState;
import lamp.alert.core.service.AlertEventProducer;
import lamp.common.metrics.MetricsProcessor;
import lamp.common.metrics.MetricsTarget;
import lamp.common.metrics.TargetMetrics;
import lamp.common.utils.ExceptionUtils;

import java.util.Date;
import java.util.List;

public class MetricsWatcher implements MetricsProcessor {

	private MetricsAlarmDefinitionProvider metricsAlarmDefinitionProvider;
	private AlertEventProducer alertEventProducer;

	public MetricsWatcher(MetricsAlarmDefinitionProvider metricsAlarmDefinitionProvider, AlertEventProducer alertEventProducer) {
		this.metricsAlarmDefinitionProvider = metricsAlarmDefinitionProvider;
		this.alertEventProducer = alertEventProducer;
	}

	@Override
	public void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
		if (targetMetrics == null) {
			targetMetrics = new TargetMetrics();
			targetMetrics.setId(metricsTarget.getId());
			targetMetrics.setName(metricsTarget.getName());
		}

		watch(targetMetrics);
	}

	protected void watch(TargetMetrics targetMetrics) {
		Date stateTime = new Date();
		List<AlertRule> alertRules = metricsAlarmDefinitionProvider.getMetricsAlarmDefinitions();
		for (AlertRule alertRule : alertRules) {
			AlertEvent alertEvent = new AlertEvent();
			alertEvent.setTenantId(targetMetrics.getId());
			alertEvent.setAlarmDefinitionId(alertRule.getId());
			alertEvent.setAlarmType(alertRule.getType());
			alertEvent.setSeverity(alertRule.getSeverity());
			alertEvent.setDimension(targetMetrics.getMetrics());
			alertEvent.setStateTime(stateTime);

			AlertRuleExpression expression = alertRule.getExpression();
			try {
				AlertState state = expression.evaluate(targetMetrics);
				alertEvent.setState(state);
			} catch (Throwable t) {
				alertEvent.setState(AlertState.UNDETERMINED);
				alertEvent.setStateDescription(ExceptionUtils.getStackTrace(t));
			}

			alertEventProducer.send(alertEvent);
		}
	}

}
