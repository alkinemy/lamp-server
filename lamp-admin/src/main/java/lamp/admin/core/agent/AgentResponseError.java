package lamp.admin.core.agent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentResponseError {

	private String code;
	private String message;
	private String stacktrace;

}
