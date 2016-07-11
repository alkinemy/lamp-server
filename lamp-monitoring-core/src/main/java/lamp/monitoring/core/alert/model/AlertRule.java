package lamp.monitoring.core.alert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AlertRule {

    private String id;
    private String name;
    private String description;

    private AlertSeverity severity = AlertSeverity.WARN;

    private List<String> okActions;
    private List<String> alertActions;
    private List<String> undeterminedActions;

    private boolean enabled;

}
