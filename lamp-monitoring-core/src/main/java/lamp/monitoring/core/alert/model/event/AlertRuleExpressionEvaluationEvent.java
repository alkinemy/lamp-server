package lamp.monitoring.core.alert.model.event;

import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.alert.model.AlertState;
import lamp.common.monitoring.model.Tenant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
public class AlertRuleExpressionEvaluationEvent implements AlertEvent {

    private Tenant tenant;
    private AlertRule<?> alertRule;

    private Date timestamp;

    private AlertState state;
    private String reason;
    private Map<String, Object> reasonData;

}
