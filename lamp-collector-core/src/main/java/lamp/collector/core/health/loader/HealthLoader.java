package lamp.collector.core.health.loader;


import lamp.collector.core.base.Endpoint;
import lamp.collector.core.health.HealthStatus;

public interface HealthLoader {

    HealthStatus getHealth(Endpoint endpoint);

}
