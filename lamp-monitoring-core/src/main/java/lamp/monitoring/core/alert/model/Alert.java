package lamp.monitoring.core.alert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
public class Alert {

    private String tenantId;
    private String alarmDefinitionId;
    private Map<String, Object> dimension;
    private AlertState state;
    private Date stateTime;
    private AlertStateHistory lastStateHistory;

    public AlertStateHistory getLastStateHistory() {
        return lastStateHistory;
    }
}
