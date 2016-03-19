package lamp.monitoring.core.alert.model;

public interface AlertRuleExpression<T> {

	AlertState evaluate(T context);

}
