package lamp.server.aladin.api.support.jwt;

import lamp.server.aladin.core.exception.MessageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class InvalidAuthenticationTokenException extends MessageException {

	private static final String DEFAULT_MESSAGE = "인증 토큰이 유효하지 않습니다.";
	private static final String CODE = "INVALID_AUTH_TOKEN";

	public InvalidAuthenticationTokenException(Object[] args) {
		super(DEFAULT_MESSAGE, CODE, args);
	}

	public InvalidAuthenticationTokenException(Throwable t, Object[] args) {
		super(DEFAULT_MESSAGE, t, CODE, args);
	}

}
