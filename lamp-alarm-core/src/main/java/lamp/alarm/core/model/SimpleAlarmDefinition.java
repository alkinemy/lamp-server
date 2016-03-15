package lamp.alarm.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SimpleAlarmDefinition {

	private String id;
	private String name;

	private AlarmSeverity severity;

	private List<String> okActions;
	private List<String> alarmActions;
	private List<String> undeterminedActions;

	private AlarmExpression expression;
}
