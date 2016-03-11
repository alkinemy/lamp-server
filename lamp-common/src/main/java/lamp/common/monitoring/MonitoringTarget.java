package lamp.common.monitoring;

import lamp.common.metrics.HealthTarget;
import lamp.common.metrics.MetricsTarget;

public interface MonitoringTarget extends HealthTarget, MetricsTarget {

	Boolean getHealthMonitoringEnabled();

	Boolean getMetricsMonitoringEnabled();
}
