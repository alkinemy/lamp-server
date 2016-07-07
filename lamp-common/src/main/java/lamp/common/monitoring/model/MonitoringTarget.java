package lamp.common.monitoring.model;

import lamp.common.collector.model.CollectionTarget;

public interface MonitoringTarget extends CollectionTarget {

    String getTenantId();

}
