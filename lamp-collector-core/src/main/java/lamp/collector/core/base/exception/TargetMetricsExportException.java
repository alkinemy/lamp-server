package lamp.collector.core.base.exception;


public class TargetMetricsExportException extends RuntimeException {

    public TargetMetricsExportException(String message) {
        super(message);
    }

    public TargetMetricsExportException(String message, Throwable cause) {
        super(message, cause);
    }

}
