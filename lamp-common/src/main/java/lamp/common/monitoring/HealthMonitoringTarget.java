package lamp.common.monitoring;

import lamp.common.metrics.HealthTarget;
import lamp.common.metrics.MetricsTarget;

public interface HealthMonitoringTarget extends HealthTarget, MetricsTarget {

	Boolean getHealthMonitoringEnabled();

	Boolean getMetricsMonitoringEnabled();
}
