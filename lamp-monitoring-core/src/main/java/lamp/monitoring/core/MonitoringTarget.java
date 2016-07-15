package lamp.monitoring.core;

import lamp.common.collector.model.CollectionTarget;

public interface MonitoringTarget extends CollectionTarget {

    String getTenantId();

}
