package lamp.monitoring.core.metrics.rule.spel;


import lamp.monitoring.core.base.alert.model.AlertEvent;
import lamp.monitoring.core.metrics.MonitoringTargetMetrics;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class SpelTargetMetricsAlertEvent extends AlertEvent<MonitoringTargetMetrics, SpelTargetMetricsAlertRule> {

}
