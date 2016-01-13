package lamp.server.aladin.agent.exception;

import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.core.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice(annotations = { RestController.class})
public class AgentExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public AgentError handleException(Exception ex) {
		log.warn("handleException", ex);
		AgentError error = new AgentError();
		error.setCode(AdminErrorCode.INTERNAL.name());
		error.setMessage(AdminErrorCode.INTERNAL.getDefaultMessage());
		return error;
	}

	@ExceptionHandler(MessageException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public AgentError handleException(MessageException ex) {
		log.warn("handleMessageException", ex);
		AgentError error = new AgentError();
		error.setCode(ex.getCode());
		error.setMessage(ex.getMessage());
		return error;
	}

}
