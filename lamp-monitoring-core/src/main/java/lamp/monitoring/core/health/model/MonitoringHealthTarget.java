package lamp.monitoring.core.health.model;

import lamp.common.monitoring.model.Tenant;

public interface MonitoringHealthTarget extends Tenant {

    boolean isHealthMonitoringEnabled();

}
