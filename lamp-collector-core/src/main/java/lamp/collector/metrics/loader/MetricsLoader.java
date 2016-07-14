package lamp.collector.metrics.loader;


import lamp.collector.metrics.MetricsEndpoint;

import java.util.Map;

public interface MetricsLoader {

    Map<String, Object> getMetrics(MetricsEndpoint metricsEndpoint);
    
}
