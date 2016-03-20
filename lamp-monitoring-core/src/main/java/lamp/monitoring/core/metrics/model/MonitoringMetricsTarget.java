package lamp.monitoring.core.metrics.model;

import lamp.common.monitoring.model.Tenant;

public interface MonitoringMetricsTarget extends Tenant {

    boolean isMetricsMonitoringEnabled();

}
