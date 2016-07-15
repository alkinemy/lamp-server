package lamp.common.monitoring.model;

import lamp.common.collector.model.TargetMetrics;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class MonitoringTargetMetrics extends TargetMetrics implements MonitoringTarget {

	private String tenantId;

}
