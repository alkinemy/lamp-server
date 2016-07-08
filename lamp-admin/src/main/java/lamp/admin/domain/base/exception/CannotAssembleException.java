package lamp.admin.domain.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CannotAssembleException extends MessageException {

	private static final String DEFAULT_MESSAGE = "데이터를 변환할 수 없습니다.";
	private static final String DEFAULT_CODE = "CANNOT_ASSEMBLE";

	public CannotAssembleException() {
		super(DEFAULT_MESSAGE, DEFAULT_CODE, null);
	}

	public CannotAssembleException(Throwable cause, Object... args) {
		super(DEFAULT_MESSAGE, cause, DEFAULT_CODE, args);
	}

	public CannotAssembleException(String defaultMessage, String code, Object... args) {
		super(defaultMessage, code, args);
	}

	public CannotAssembleException(String defaultMessage, Throwable cause, String code, Object... args) {
		super(defaultMessage, cause, code, args);
	}

}
