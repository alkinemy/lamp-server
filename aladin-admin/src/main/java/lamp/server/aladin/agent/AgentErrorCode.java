package lamp.server.aladin.agent;

import lamp.server.aladin.core.exception.ErrorCode;
import lamp.server.aladin.core.exception.MessageException;

public enum AgentErrorCode implements ErrorCode {
	INTERNAL("에러가 발생하였습니다."),
	INVALID_AUTH_TOKEN("인증 토큰이 유효하지 않습니다.");

	private String defaultMessage;
	private Class<? extends MessageException> exceptionClass;

	AgentErrorCode(String defaultMessage) {
		this(defaultMessage, MessageException.class);
	}

	AgentErrorCode(String defaultMessage, Class<? extends MessageException> exceptionClass) {
		this.defaultMessage = defaultMessage;
		this.exceptionClass = exceptionClass;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public Class<? extends MessageException> getExceptionClass() {
		return exceptionClass;
	}
}
