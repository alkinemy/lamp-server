package lamp.monitoring.core.alert.model;

import java.util.List;

public interface AlertRule<E extends AlertRuleExpression> {

    String getId();
    String getName();
    String getType();

    AlertSeverity getSeverity();

    List<String> getAlertActions();
    List<String> getUndeterminedActions();
    List<String> getOkActions();

    E getExpression();

}
