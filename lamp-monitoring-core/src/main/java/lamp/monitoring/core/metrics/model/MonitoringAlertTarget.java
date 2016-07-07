package lamp.monitoring.core.metrics.model;

import lamp.common.monitoring.model.Tenant;

@Deprecated
public interface MonitoringAlertTarget extends Tenant {

    boolean isMetricsMonitoringEnabled();

}
