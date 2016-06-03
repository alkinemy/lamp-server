package lamp.admin.domain.agent.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AgentStopForm {

	private String sshPassword;

	private String commandLine;

}
