package lamp.admin.core.support.ssh;

public class ExpectTimeoutException extends ExpectException {

	public ExpectTimeoutException(long timeout) {
		super("Expect Timeout");
	}

}
