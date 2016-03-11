package lamp.watcher.core.alarm;


public class AlarmDefinition {

    private String name;
    private String description;

    private String function; // rate
    private String metric;
    private String dimenstions;
    private String relationalOperator;
    private String value;

    private String expression;
    private String matchBy;
    private String groupBy;

    private AlarmSeverity severity;

    private String okActions;
    private String alarmActions;
    private String undeterminedActions;

}
