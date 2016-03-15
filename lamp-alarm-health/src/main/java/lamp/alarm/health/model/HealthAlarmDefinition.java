package lamp.alarm.health.model;

import lamp.alarm.core.model.AlarmDefinition;
import lamp.alarm.core.model.AlarmExpression;
import lamp.alarm.core.model.AlarmSeverity;
import lamp.alarm.core.model.AlarmType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class HealthAlarmDefinition implements AlarmDefinition {

	private String id;
	private String name;
	private String type = AlarmType.HEALTH.name();
	private AlarmSeverity severity = AlarmSeverity.WARNING;

	private List<String> okActions;
	private List<String> alarmActions;
	private List<String> undeterminedActions;

	private AlarmExpression expression = new HealthAlarmExpression();

}
