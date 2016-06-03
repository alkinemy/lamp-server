package lamp.admin.domain.agent.model;

import lamp.admin.domain.app.model.ParametersType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AgentInstallForm {

	@NotNull
	private String templateId;
	private String version;

	// SSH
	private String sshPassword;

	private ParametersType parametersType;
	private String parameters;

	private Long installScriptId;
}
