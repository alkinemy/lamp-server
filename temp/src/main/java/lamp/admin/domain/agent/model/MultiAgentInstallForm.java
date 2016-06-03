package lamp.admin.domain.agent.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MultiAgentInstallForm extends AgentInstallForm{

	private List<String> targetServerIds;
}
