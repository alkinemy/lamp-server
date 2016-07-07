package lamp.monitoring.core.metrics.service;

import lamp.monitoring.core.alert.AlertEventProducer;
import lamp.monitoring.core.alert.model.AlertEvent;

import lamp.monitoring.core.metrics.model.TargetMetricsAlertRule;
import lamp.monitoring.core.metrics.model.TargetMetricsAlertEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TargetMetricsMonitoringProcessor extends GenericMetricsMonitoringProcessor<TargetMetricsAlertRule> {

	public TargetMetricsMonitoringProcessor(TargetMetricsAlertRuleProvider targetMetricsAlertRuleProvider, AlertEventProducer alertEventProducer) {
		super(targetMetricsAlertRuleProvider, alertEventProducer);
	}

	protected AlertEvent newAlertRuleMatchedEvent() {
		return new TargetMetricsAlertEvent();
	}

}
