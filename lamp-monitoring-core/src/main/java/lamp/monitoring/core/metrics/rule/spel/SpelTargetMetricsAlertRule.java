package lamp.monitoring.core.metrics.rule.spel;

import lamp.monitoring.core.base.alert.model.AlertRule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpelTargetMetricsAlertRule extends AlertRule {

	private String targetExpression;

	private String ruleExpression;
	private String valueExpression;

}
