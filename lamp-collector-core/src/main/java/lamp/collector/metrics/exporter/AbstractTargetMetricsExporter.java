package lamp.collector.metrics.exporter;

import lamp.collector.metrics.TargetMetrics;

public abstract class AbstractTargetMetricsExporter implements TargetMetricsExporter {

    @Override
    public void process(TargetMetrics targetMetrics) {
        doProcess(targetMetrics);
    }

    protected abstract void doProcess(TargetMetrics targetMetrics);

    protected void handleException(TargetMetrics targetMetrics, Exception exception, Object... args) {

    }
}