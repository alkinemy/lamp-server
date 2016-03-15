package lamp.alarm.core.model;

public interface AlarmExpression<T> {

	AlarmState getState(T target);

}
