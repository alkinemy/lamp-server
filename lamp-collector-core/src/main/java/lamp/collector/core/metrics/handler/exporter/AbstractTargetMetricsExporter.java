package lamp.collector.core.metrics.handler.exporter;

import lamp.collector.core.metrics.TargetMetrics;

public abstract class AbstractTargetMetricsExporter implements TargetMetricsExporter {

    @Override
    public void handle(TargetMetrics targetMetrics) {
        doHandle(targetMetrics);
    }

    protected abstract void doHandle(TargetMetrics targetMetrics);

    protected void handleException(TargetMetrics targetMetrics, Exception exception, Object... args) {

    }
}