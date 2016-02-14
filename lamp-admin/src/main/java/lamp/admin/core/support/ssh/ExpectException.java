package lamp.admin.core.support.ssh;

public class ExpectException extends RuntimeException {

	public ExpectException(String message) {
		super(message);
	}

	public ExpectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpectException(Throwable cause) {
		super(cause);
	}

}
