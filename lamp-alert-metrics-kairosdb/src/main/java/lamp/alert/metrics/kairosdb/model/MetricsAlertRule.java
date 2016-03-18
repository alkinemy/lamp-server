package lamp.alert.metrics.kairosdb.model;

import lamp.alert.core.model.AlertRule;
import lamp.alert.core.model.AlertRuleExpression;
import lamp.alert.core.model.AlertSeverity;
import lamp.alert.core.model.AlertType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MetricsAlertRule implements AlertRule {

	private String id;
	private String name;
	private String type = AlertType.METRICS.name();
	private AlertSeverity severity = AlertSeverity.WARNING;

	private List<String> okActions;
	private List<String> alarmActions;
	private List<String> undeterminedActions;

	private AlertRuleExpression expression;
}
