package lamp.monitoring.core.metrics.model;

import lamp.common.monitoring.model.MonitoringTargetMetrics;
import lamp.monitoring.core.alert.model.AlertEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TargetMetricsAlertEvent extends AlertEvent<MonitoringTargetMetrics, TargetMetricsAlertRule> {

}
