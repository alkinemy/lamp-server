package lamp.admin.domain.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FlashMessageException extends MessageException {

	public FlashMessageException(String defaultMessage, String code, Object[] args) {
		super(defaultMessage, code, args);
	}

	public FlashMessageException(String defaultMessage, Throwable t, String code, Object[] args) {
		super(defaultMessage, t, code, args);
	}

}
