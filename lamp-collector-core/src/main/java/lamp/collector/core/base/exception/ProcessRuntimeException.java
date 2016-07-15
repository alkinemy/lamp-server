package lamp.collector.core.base.exception;


public class ProcessRuntimeException extends RuntimeException {

    public ProcessRuntimeException(String message) {
        super(message);
    }

    public ProcessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
