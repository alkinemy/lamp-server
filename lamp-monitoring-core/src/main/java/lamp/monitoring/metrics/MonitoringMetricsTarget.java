package lamp.monitoring.metrics;

import lamp.collector.metrics.MetricsTarget;
import lamp.monitoring.core.MonitoringTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MonitoringMetricsTarget extends MetricsTarget implements MonitoringTarget {

    private String tenantId;

}
