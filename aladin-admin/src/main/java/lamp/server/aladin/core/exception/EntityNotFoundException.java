package lamp.server.aladin.core.exception;


public class EntityNotFoundException extends MessageException {

	private static final String DEFAULT_MESSAGE = "데이터를 찾을 수가 없습니다.";
	private static final String DEFAULT_CODE = "ENTITY_NOT_FOUND";

	public EntityNotFoundException() {
		super(DEFAULT_MESSAGE, DEFAULT_CODE, null);
	}

	public EntityNotFoundException(String defaultMessage, String code, Object[] args) {
		super(defaultMessage, code, args);
	}

	public EntityNotFoundException(String defaultMessage, Throwable cause, String code, Object[] args) {
		super(defaultMessage, cause, code, args);
	}

}
