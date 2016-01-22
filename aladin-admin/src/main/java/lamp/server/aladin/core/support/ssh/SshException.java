package lamp.server.aladin.core.support.ssh;

public class SshException extends RuntimeException {

	public SshException(String message) {
		super(message);
	}

	public SshException(String message, Throwable cause) {
		super(message, cause);
	}

	public SshException(Throwable cause) {
		super(cause);
	}

}
