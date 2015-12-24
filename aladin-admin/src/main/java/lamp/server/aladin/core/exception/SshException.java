package lamp.server.aladin.core.exception;


public class SshException extends RuntimeException {

	public SshException() {
		super();
	}

	public SshException(String message) {
		super(message);
	}

	public SshException(String message, Throwable cause) {
		super(message, cause);
	}

}
