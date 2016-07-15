package lamp.monitoring.metrics.processor;

import lamp.monitoring.core.alert.AlertEventProducer;
import lamp.monitoring.core.alert.AlertRuleProcessor;
import lamp.monitoring.core.alert.AlertRuleProvider;
import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.metrics.MonitoringTargetMetrics;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MonitoringTargetMetricsAlertHandler implements MonitoringTargetMetricsHandler {

	private final AlertRuleProvider alertRuleProvider;
	private final List<AlertRuleProcessor> alertRuleProcessors;
	private final AlertEventProducer alertEventProducer;

	public MonitoringTargetMetricsAlertHandler(AlertRuleProvider alertRuleProvider,
											   List<AlertRuleProcessor> alertRuleProcessors,
											   AlertEventProducer alertEventProducer) {
		this.alertRuleProvider = alertRuleProvider;
		this.alertRuleProcessors = alertRuleProcessors;
		this.alertEventProducer = alertEventProducer;
	}

	@Override
	public void handle(MonitoringTargetMetrics targetMetrics) {
		List<? extends AlertRule> alertRules = alertRuleProvider.getAlertRules();
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
