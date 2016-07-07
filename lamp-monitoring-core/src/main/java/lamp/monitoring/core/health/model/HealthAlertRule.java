package lamp.monitoring.core.health.model;

import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.alert.model.AlertRuleExpression;
import lamp.monitoring.core.alert.model.AlertSeverity;
import lamp.monitoring.core.alert.model.AlertType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public abstract class HealthAlertRule implements AlertRule {

	private String id;
	private String name;
	private String type = AlertType.HEALTH.name();
	private AlertSeverity severity = AlertSeverity.WARN;

	private List<String> okActions;
	private List<String> alertActions;
	private List<String> undeterminedActions;

	private AlertRuleExpression expression = new HealthAlertRuleExpression();

}
