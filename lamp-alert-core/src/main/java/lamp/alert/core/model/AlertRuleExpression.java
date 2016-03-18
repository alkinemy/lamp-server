package lamp.alert.core.model;

public interface AlertRuleExpression<T> {

	AlertState evaluate(T context);

}
