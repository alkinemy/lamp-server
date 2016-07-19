package lamp.collector.core.metrics.handler.exporter;

import lamp.collector.core.metrics.TargetMetrics;
import lamp.common.event.Event;
import lamp.common.event.EventPublisher;

public abstract class AbstractTargetMetricsExporter implements TargetMetricsExporter {

    private EventPublisher eventPublisher;

    public AbstractTargetMetricsExporter() {
        this(null);
    }

    public AbstractTargetMetricsExporter(EventPublisher eventPublisher) {
        if (eventPublisher != null) {
            this.eventPublisher = eventPublisher;
        } else {
            this.eventPublisher = new EventPublisher() {
                @Override public void publish(Event event) {

                }
            };
        }

    }

    @Override
    public void handle(TargetMetrics targetMetrics) {
        doHandle(targetMetrics);
    }

    protected abstract void doHandle(TargetMetrics targetMetrics);

    protected void handleException(TargetMetrics targetMetrics, Exception exception, Object... args) {
//        String name, Throwable e, EventTarget target;
//
//        Event event = new Event(EventLevel.WARN, );
//
//        eventPublisher.publish();
    }
}