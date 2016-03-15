package lamp.alarm.core.model;

import java.util.List;

public interface AlarmDefinition {

    String getId();
    String getName();
    String getType();

    AlarmSeverity getSeverity();

    List<String> getOkActions();
    List<String> getAlarmActions();
    List<String> getUndeterminedActions();

    AlarmExpression getExpression();

}
