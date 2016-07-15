package lamp.monitoring.metrics;


import lamp.collector.core.metrics.TargetMetrics;
import lamp.monitoring.core.MonitoringTarget;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringTargetMetrics extends TargetMetrics implements MonitoringTarget {

	private String tenantId;


	public MonitoringTargetMetrics(TargetMetrics targetMetrics, String tenantId) {
		setId(targetMetrics.getId());
		setTags(targetMetrics.getTags());
		setTimestamp(targetMetrics.getTimestamp());
		setMetrics(targetMetrics.getMetrics());
		setException(targetMetrics.getException());

		setTenantId(tenantId);
	}

	public MonitoringTargetMetrics(MonitoringMetricsTarget target, Exception e) {
		this(new TargetMetrics(target, e), target.getTenantId());
	}

}
