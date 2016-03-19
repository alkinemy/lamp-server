package lamp.monitoring.core.metrics.model;

import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.alert.model.AlertSeverity;
import lamp.monitoring.core.alert.model.AlertType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MetricsAlertRule<E> implements AlertRule {

	private String id;
	private String name;
	private String type = AlertType.METRICS.name();
	private AlertSeverity severity = AlertSeverity.WARNING;

	private List<String> okActions;
	private List<String> alarmActions;
	private List<String> undeterminedActions;

	private E expression;
}
