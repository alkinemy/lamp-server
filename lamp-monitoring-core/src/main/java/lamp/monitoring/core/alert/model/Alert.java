package lamp.monitoring.core.alert.model;

import lamp.common.monitoring.model.Tenant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Alert {

    private String id;

    private Tenant tenant;
    private AlertRule<?> rule;

    private AlertState oldState;
    private AlertState newState;
    private Date timestamp;


}
