package lamp.watcher.alarm;


public class AlarmDefinition {

    private String name;
    private String description;

    private String function; // rate
    private String metric;
    private String dimenstions;
    private String reliationalOperator;
    private String value;

    private String expression;
    private String matchBy;

    private AlarmSeverity severity;
    private String okActions;
    private String alarmActions;
    private String undeterminedActions;

}
