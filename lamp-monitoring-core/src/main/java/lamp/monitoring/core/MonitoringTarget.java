package lamp.monitoring.core;

import lamp.collector.core.base.CollectionTarget;

public interface MonitoringTarget extends CollectionTarget {

    String getTenantId();

}
