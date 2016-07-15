package lamp.monitoring.metrics.loader;

import lamp.collector.metrics.TargetMetrics;
import lamp.collector.metrics.loader.http.TargetMetricsHttpLoader;
import lamp.monitoring.metrics.MonitoringMetricsTarget;
import lamp.monitoring.metrics.MonitoringTargetMetrics;

public class MonitoringTargetMetricsHttpLoader extends TargetMetricsHttpLoader implements MonitoringTargetMetricsLoader {

    @Override
    public MonitoringTargetMetrics getMetrics(MonitoringMetricsTarget metricsTarget) {
        TargetMetrics targetMetrics = super.getMetrics(metricsTarget);
        return new MonitoringTargetMetrics(targetMetrics, metricsTarget.getTenantId());
    }

}
