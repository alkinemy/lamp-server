package lamp.server.aladin.core.exception;

public interface ErrorCode {

	String name();

	Class<? extends MessageException> getExceptionClass();

	String getDefaultMessage();

}
