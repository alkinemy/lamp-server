package lamp.admin.domain.support.agent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentResponseError {

	private String code;
	private String message;
	private String stacktrace;

}
