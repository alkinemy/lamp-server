package lamp.monitoring.core.alert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
public class AlertStateHistory {

    private String id;

    private String alertId;
    private String alertRuleId;
    private String tenantId;
    private Date stateTime;
    private AlertState state;
    private String stateDescription;

    private String alertType;
    private AlertSeverity severity;
    private AlertCondition condition;

    private Map<String, Object> dimension;
}
