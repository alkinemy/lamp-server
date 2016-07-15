package lamp.collector.core.metrics.handler;


public interface AsyncExecuteCallback<T> {

    void onCompletion(T target, Exception exception);
}
