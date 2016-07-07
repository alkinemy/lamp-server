package lamp.monitoring.core.alert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlertEventHistory {

    private String id;

    private String alertId;

    private long eventTime;
    private AlertEvent event;

}
