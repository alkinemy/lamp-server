package lamp.monitoring.core.alert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public abstract class AbstractAlertRule<T, E extends AlertRuleExpression> implements AlertRule<T, E> {

	private String id;
	private String name;
//	private String type;
	private AlertSeverity severity = AlertSeverity.WARN;

	private List<String> okActions;
	private List<String> alertActions;
	private List<String> undeterminedActions;

	private E ruleExpression;
}
