package lamp.admin.core.support.agent;

import lamp.admin.core.base.exception.MessageException;
import lombok.Getter;

public class AgentResponseErrorException extends MessageException {

	@Getter
	private AgentResponseError agentError;

	public AgentResponseErrorException(AgentResponseError agentError) {
		super(agentError.getMessage(), agentError.getCode(), null);
		this.agentError = agentError;
	}

	public AgentResponseErrorException(AgentResponseError agentError, Throwable t) {
		super(agentError.getMessage(), t, agentError.getCode(), null);
		this.agentError = agentError;
	}

}
