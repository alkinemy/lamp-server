package lamp.collector.core.health.handler.exporter;


import lamp.collector.core.health.TargetHealth;

public abstract class AbstractTargetHealthExporter implements TargetHealthExporter {

    @Override
    public void handle(TargetHealth targetHealth) {
        doHandle(targetHealth);
    }

    protected abstract void doHandle(TargetHealth targetHealth);

    protected void handleException(TargetHealth targetHealth, Exception exception, Object... args) {

    }
}