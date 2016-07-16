package lamp.monitoring.core.metrics.service;

import lamp.common.collector.model.TargetMetrics;

public class MetricsWatcher {

//	private SpelTargetMetricsAlertRuleProvider metricsAlertRuleProvider;
//	private AlertEventProducer alertEventProducer;
//
//	public MetricsWatcher(SpelTargetMetricsAlertRuleProvider metricsAlertRuleProvider, AlertEventProducer alertEventProducer) {
//		this.metricsAlertRuleProvider = metricsAlertRuleProvider;
//		this.alertEventProducer = alertEventProducer;
//	}
//
//
//	public void handle(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
//		if (targetMetrics == null) {
//			targetMetrics = new TargetMetrics();
//			targetMetrics.setId(metricsTarget.getId());
//			targetMetrics.setName(metricsTarget.getName());
//		}
//
//		watch(targetMetrics);
//	}

	protected void watch(TargetMetrics targetMetrics) {
//		Date stateTime = new Date();
//		List<AlertRule> alertRules = metricsAlertRuleProvider.getMetricsAlertRules();
//		for (AlertRule alertRule : alertRules) {
//			AlertEvent alertEvent = new AlertEvent();
//			alertEvent.setTenantId(targetMetrics.getId());
//			alertEvent.setAlertRuleId(alertRule.getId());
//			alertEvent.setAlertType(alertRule.getType());
//			alertEvent.setSeverity(alertRule.getSeverity());
//			alertEvent.setDimension(targetMetrics.getHealth());
//			alertEvent.setStateTime(stateTime);
//
//			AlertRuleExpression expression = alertRule.getExpression();
//			try {
//				AlertState state = expression.evaluate(targetMetrics);
//				alertEvent.setState(state);
//			} catch (Throwable t) {
//				alertEvent.setState(AlertState.UNDETERMINED);
//				alertEvent.setStateDescription(ExceptionUtils.getStackTrace(t));
//			}
//
//			alertEventProducer.send(alertEvent);
//		}
	}

}
