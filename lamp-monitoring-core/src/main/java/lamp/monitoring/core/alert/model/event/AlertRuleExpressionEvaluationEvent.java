package lamp.monitoring.core.alert.model.event;

import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertSeverity;
import lamp.monitoring.core.alert.model.AlertState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
public class AlertRuleExpressionEvaluationEvent implements AlertEvent {

    private String alertType;

    private String tenantId;
    private String alertRuleId;
    private AlertState state;
    private String stateDescription;
    private Date stateTime;
    private AlertSeverity severity;

    private Map<String, Object> dimension;

}
