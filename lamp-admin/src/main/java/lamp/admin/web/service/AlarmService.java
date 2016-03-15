package lamp.admin.web.service;

import lamp.alarm.core.model.AlarmDefinition;
import lamp.alarm.health.model.HealthAlarmDefinition;
import lamp.alarm.health.service.HealthAlarmDefinitionProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AlarmService implements HealthAlarmDefinitionProvider {

	@Override public List<AlarmDefinition> getHealthAlarmDefinitions() {
		List<AlarmDefinition> alarmDefinitions = new ArrayList<>();
		HealthAlarmDefinition alarmDefinition = new HealthAlarmDefinition();
		alarmDefinition.setId("health");
		alarmDefinition.setName("Health Alarm");
		alarmDefinitions.add(alarmDefinition);

		return alarmDefinitions;
	}

}
