package lamp.admin.domain.base.exception;

public class DuplicatedException extends MessageException {

	private static final String DEFAULT_MESSAGE = "중복된 데이터 입니다.";
	private static final String DEFAULT_CODE = "DUPLICATED";

	public DuplicatedException() {
		super(DEFAULT_MESSAGE, DEFAULT_CODE, null);
	}

	public DuplicatedException(String defaultMessage, String code, Object[] args) {
		super(defaultMessage, code, args);
	}

	public DuplicatedException(String defaultMessage, Throwable cause, String code, Object[] args) {
		super(defaultMessage, cause, code, args);
	}

}
