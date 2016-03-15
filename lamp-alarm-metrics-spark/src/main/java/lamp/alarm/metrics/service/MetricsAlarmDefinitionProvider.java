package lamp.alarm.metrics.service;

import lamp.alarm.core.model.AlarmDefinition;

import java.util.List;

public interface MetricsAlarmDefinitionProvider {

	List<AlarmDefinition> getMetricsAlarmDefinitions();

}
