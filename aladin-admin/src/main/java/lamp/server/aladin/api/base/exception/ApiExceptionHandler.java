package lamp.server.aladin.api.base.exception;

import lamp.server.aladin.domain.base.DomainErrorCode;
import lamp.server.aladin.common.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleException(Exception ex) {
		log.warn("handleException", ex);
		ApiError error = new ApiError();
		error.setCode(DomainErrorCode.INTERNAL.name());
		error.setMessage(DomainErrorCode.INTERNAL.getDefaultMessage());
		return error;
	}

	@ExceptionHandler(MessageException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleException(MessageException ex) {
		log.warn("handleMessageException", ex);
		ApiError error = new ApiError();
		error.setCode(ex.getCode());
		error.setMessage(ex.getMessage());
		return error;
	}

}
