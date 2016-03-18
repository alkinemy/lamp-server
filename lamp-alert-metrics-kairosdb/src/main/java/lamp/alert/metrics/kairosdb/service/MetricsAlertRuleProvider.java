package lamp.alert.metrics.kairosdb.service;

import lamp.alert.core.model.AlertRule;

import java.util.List;

public interface MetricsAlertRuleProvider {

	List<AlertRule> getMetricsAlarmDefinitions();

}
