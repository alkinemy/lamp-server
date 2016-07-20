package lamp.collector.core.metrics.handler.exporter;

import lamp.collector.core.base.event.EventName;
import lamp.collector.core.metrics.TargetMetrics;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTargetMetricsExporter implements TargetMetricsExporter {

    private EventPublisher eventPublisher;

    public AbstractTargetMetricsExporter(EventPublisher eventPublisher) {
        if (eventPublisher != null) {
            this.eventPublisher = eventPublisher;
        } else {
            this.eventPublisher = event -> {

            };
        }

    }

    @Override
    public void handle(TargetMetrics targetMetrics) {
        doHandle(targetMetrics);
    }

    protected abstract void doHandle(TargetMetrics targetMetrics);

    protected void handleException(TargetMetrics targetMetrics, String message, Exception exception, Object... args) {
        log.warn(message, exception);

        Event event = new Event(EventLevel.WARN, EventName.TARGET_METRICS_EXPORT_FAILED, message, exception, targetMetrics);
        publish(event);
    }

    protected void publish(Event event) {
        eventPublisher.publish(event);
    }

}