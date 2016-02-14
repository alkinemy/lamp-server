package lamp.admin.core.base.exception;

public interface ErrorCode {

	String name();

	Class<? extends MessageException> getExceptionClass();

	String getDefaultMessage();

}
