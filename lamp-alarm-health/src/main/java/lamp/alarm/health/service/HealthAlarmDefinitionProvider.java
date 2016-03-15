package lamp.alarm.health.service;

import lamp.alarm.core.model.AlarmDefinition;

import java.util.List;


public interface HealthAlarmDefinitionProvider {

	List<AlarmDefinition> getHealthAlarmDefinitions();

}
