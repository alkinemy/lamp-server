package lamp.monitoring.core.health;


import lamp.collector.core.health.HealthTarget;
import lamp.monitoring.core.MonitoringTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MonitoringHealthTarget extends HealthTarget implements MonitoringTarget {

    private String tenantId;

}
