package lamp.collector.metrics.loader;


import lamp.collector.core.Endpoint;

import java.util.Map;

public interface MetricsLoader {

    Map<String, Object> getMetrics(Endpoint endpoint);

}
