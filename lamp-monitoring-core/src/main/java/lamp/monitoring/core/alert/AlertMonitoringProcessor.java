package lamp.monitoring.core.alert;

import lamp.common.monitoring.model.MonitoringTargetMetrics;
import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertRule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AlertMonitoringProcessor {

	private final AlertRuleProvider alertRuleProvider;

	private final List<AlertRuleProcessor> alertRuleProcessors;

	private AlertEventProducer alertEventProducer;

	public AlertMonitoringProcessor(AlertRuleProvider alertRuleProvider,
									List<AlertRuleProcessor> alertRuleProcessors,
									AlertEventProducer alertEventProducer) {
		this.alertRuleProvider = alertRuleProvider;
		this.alertRuleProcessors = alertRuleProcessors;
		this.alertEventProducer = alertEventProducer;
	}

	public void monitoring(MonitoringTargetMetrics targetMetrics) {
		List<AlertRule> alertRules = alertRuleProvider.getAlertRules();
		for (AlertRule alertRule : alertRules) {
			for (AlertRuleProcessor alertRuleProcessor : alertRuleProcessors) {
				try {
					if (alertRuleProcessor.canProcess(alertRule)) {
						AlertEvent alertEvent = alertRuleProcessor.doProcess(targetMetrics, alertRule);
						if (alertEvent != null) {
							alertEventProducer.send(alertEvent);
						}
					}
				} catch (Exception e) {
					log.warn("AlertRuleProcessor processing failed", e);
				}
			}
		}


	}
}
