package lamp.monitoring.core.metrics.rule.spel;

import lamp.monitoring.core.base.alert.model.AlertEvent;
import lamp.monitoring.core.base.alert.model.AlertRule;
import lamp.monitoring.core.base.alert.model.AlertRuleEvaluator;
import lamp.monitoring.core.metrics.MonitoringTargetMetrics;
import lamp.monitoring.core.metrics.rule.AbstractMetricsAlertRuleProcessor;
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
