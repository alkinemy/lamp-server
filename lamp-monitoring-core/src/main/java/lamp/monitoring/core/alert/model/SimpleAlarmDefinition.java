package lamp.monitoring.core.alert.model;

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

	private AlertSeverity severity;

	private List<String> okActions;
	private List<String> alarmActions;
	private List<String> undeterminedActions;

	private AlertRuleExpression expression;
}
