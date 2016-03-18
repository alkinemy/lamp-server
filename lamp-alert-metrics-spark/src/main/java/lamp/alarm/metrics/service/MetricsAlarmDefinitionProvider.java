package lamp.alarm.metrics.service;

import lamp.alert.core.model.AlertRule;

import java.util.List;

public interface MetricsAlarmDefinitionProvider {

	List<AlertRule> getMetricsAlarmDefinitions();

}
