package lamp.monitoring.core.alert.model;

import java.util.List;

public interface AlertRule<E extends AlertRuleExpression> {

    String getId();
    String getName();
    String getType();

    AlertSeverity getSeverity();

    List<String> getOkActions();
    List<String> getAlarmActions();
    List<String> getUndeterminedActions();

    E getExpression();

}
