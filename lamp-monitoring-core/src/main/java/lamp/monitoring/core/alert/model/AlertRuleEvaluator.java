package lamp.monitoring.core.alert.model;

public interface AlertRuleEvaluator<R extends AlertRule, T> {

	boolean isAlertTarget(R rule, T target);

	AlertState evaluate(R rule, T target);

}
