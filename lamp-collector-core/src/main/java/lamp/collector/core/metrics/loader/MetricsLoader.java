package lamp.collector.core.metrics.loader;


import lamp.collector.core.base.Endpoint;

import java.util.Map;

public interface MetricsLoader {

    Map<String, Object> getMetrics(Endpoint endpoint);

}
