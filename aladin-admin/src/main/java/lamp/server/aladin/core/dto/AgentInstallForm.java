package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AgentInstallForm {

	@NotNull
	private Long templateId;
	private String version;

}
