package lamp.monitoring.core.metrics.service;

import lamp.common.monitoring.model.MonitoringTargetMetrics;
import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.alert.model.AlertRuleEvaluator;
import lamp.monitoring.core.metrics.model.SpelTargetMetricsAlertEvent;
import lamp.monitoring.core.metrics.model.SpelTargetMetricsAlertRule;
import lamp.monitoring.core.metrics.model.SpelTargetMetricsAlertRuleEvaluator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpelTargetMetricsAlertRuleProcessor extends AbstractMetricsAlertRuleProcessor<SpelTargetMetricsAlertRule> {

	private SpelTargetMetricsAlertRuleEvaluator alertRuleEvaluator = new SpelTargetMetricsAlertRuleEvaluator();

	@Override public boolean canProcess(AlertRule alertRule) {
		return alertRule instanceof SpelTargetMetricsAlertRule;
	}

	@Override protected AlertEvent newAlertRuleMatchedEvent() {
		return new SpelTargetMetricsAlertEvent();
	}

	@Override protected AlertRuleEvaluator<SpelTargetMetricsAlertRule, MonitoringTargetMetrics> getAlertRuleEvaluator() {
		return alertRuleEvaluator;
	}

}
