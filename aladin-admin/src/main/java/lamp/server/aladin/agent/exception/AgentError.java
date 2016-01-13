package lamp.server.aladin.agent.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentError {

	private String code;
	private String message;
	private String stacktrace;

}
