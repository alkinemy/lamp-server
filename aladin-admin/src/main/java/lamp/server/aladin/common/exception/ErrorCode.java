package lamp.server.aladin.common.exception;

public interface ErrorCode {

	String name();

	Class<? extends MessageException> getExceptionClass();

	String getDefaultMessage();

}
