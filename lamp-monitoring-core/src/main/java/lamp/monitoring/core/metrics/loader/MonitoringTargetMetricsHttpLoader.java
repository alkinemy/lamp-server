package lamp.monitoring.core.metrics.loader;

import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.loader.http.TargetMetricsHttpLoader;
import lamp.monitoring.core.metrics.MonitoringMetricsTarget;
import lamp.monitoring.core.metrics.MonitoringTargetMetrics;

public class MonitoringTargetMetricsHttpLoader implements MonitoringTargetMetricsLoader {

    private final TargetMetricsHttpLoader targetMetricsHttpLoader = new TargetMetricsHttpLoader();

    @Override
    public MonitoringTargetMetrics getMetrics(MonitoringMetricsTarget metricsTarget) {
        TargetMetrics targetMetrics = targetMetricsHttpLoader.getMetrics(metricsTarget);
        return new MonitoringTargetMetrics(targetMetrics, metricsTarget.getTenantId());
    }

}
