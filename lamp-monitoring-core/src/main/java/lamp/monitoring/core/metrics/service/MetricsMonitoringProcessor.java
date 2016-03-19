package lamp.monitoring.core.metrics.service;

import lamp.common.collector.service.MetricsProcessor;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lamp.monitoring.core.metrics.model.MonitoringMetricsTarget;

public abstract class MetricsMonitoringProcessor implements MetricsProcessor {

    @Override
    public boolean canProcess(MetricsTarget metricsTarget) {
        if (metricsTarget instanceof MonitoringMetricsTarget) {
            return ((MonitoringMetricsTarget) metricsTarget).isMetricsMonitoringEnabled();
        }
        return false;
    }

    @Override
    public void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
        monitoring(metricsTarget);
    }

    abstract protected void monitoring(MetricsTarget metricsTarget);

}
