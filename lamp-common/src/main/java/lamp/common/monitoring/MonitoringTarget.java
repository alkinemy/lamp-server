package lamp.common.monitoring;

import lamp.common.collection.CollectionTarget;

public interface MonitoringTarget extends CollectionTarget {

	Boolean getHealthMonitoringEnabled();

	Boolean getMetricsMonitoringEnabled();
}
