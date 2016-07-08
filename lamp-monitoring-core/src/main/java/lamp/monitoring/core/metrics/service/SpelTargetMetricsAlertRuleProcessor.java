package lamp.monitoring.core.metrics.service;

import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.metrics.model.SpelTargetMetricsAlertEvent;
import lamp.monitoring.core.metrics.model.SpelTargetMetricsAlertRule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpelTargetMetricsAlertRuleProcessor extends AbstractMetricsAlertRuleProcessor<SpelTargetMetricsAlertRule> {

	@Override public boolean canProcess(AlertRule alertRule) {
		return SpelTargetMetricsAlertRule.class.equals(alertRule.getClass());
	}

	@Override protected AlertEvent newAlertRuleMatchedEvent() {
		return new SpelTargetMetricsAlertEvent();
	}


}
