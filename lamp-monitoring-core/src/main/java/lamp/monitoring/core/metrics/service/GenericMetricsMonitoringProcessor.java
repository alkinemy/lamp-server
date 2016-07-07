package lamp.monitoring.core.metrics.service;

import lamp.common.monitoring.model.MonitoringTargetMetrics;
import lamp.common.utils.ExceptionUtils;
import lamp.monitoring.core.alert.AlertEventProducer;
import lamp.monitoring.core.alert.AlertRuleProvider;
import lamp.monitoring.core.alert.MonitoringProcessor;
import lamp.monitoring.core.alert.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class GenericMetricsMonitoringProcessor<R extends AlertRule> implements MonitoringProcessor<MonitoringTargetMetrics> {

	private AlertRuleProvider<R> alertRuleProvider;
	private AlertEventProducer alertEventProducer;

	public GenericMetricsMonitoringProcessor(AlertRuleProvider<R> alertRuleProvider, AlertEventProducer alertEventProducer) {
		this.alertRuleProvider = alertRuleProvider;
		this.alertEventProducer = alertEventProducer;
	}

	public void monitoring(MonitoringTargetMetrics targetMetrics) {
		long timestamp = System.currentTimeMillis();
		List<R> alertRules = alertRuleProvider.getAlertRules();

		for (AlertRule alertRule : alertRules) {
			try {
				if (alertRule.isAlertTarget(targetMetrics)) {

					AlertEvent event = newAlertRuleMatchedEvent();
					event.setTimestamp(timestamp);
					event.setTarget(targetMetrics);
					event.setRule(alertRule);

					try {
						AlertRuleExpression expression = alertRule.getRuleExpression();
						//			event.setTenant(targetHealth);

						AlertState state = expression.evaluate(targetMetrics);
						event.setState(state);
					} catch (Throwable t) {
						event.setState(new AlertState(AlertStateCode.UNDETERMINED, ExceptionUtils.getStackTrace(t)));
					}
					alertEventProducer.send(event);
				}

			} catch (Exception e) {
				log.warn("Metrics alert rule check failed", e);
			}
		}

	}

	protected abstract AlertEvent newAlertRuleMatchedEvent();
}
