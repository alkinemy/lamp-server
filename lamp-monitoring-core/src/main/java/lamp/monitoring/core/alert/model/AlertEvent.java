package lamp.monitoring.core.alert.model;

import lamp.common.monitoring.model.MonitoringTarget;
import lamp.common.monitoring.model.Tenant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlertEvent<T extends MonitoringTarget, R extends AlertRule> {

    private long timestamp;

    private T target;

    private R rule;

    private AlertState state;

    private Tenant tenant;

}
