package lamp.admin.domain.old.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AppDeployForm {

	private String id;
	private String name;
	private String description;

	@NotNull
	private String templateId;
	private String version;

	private Boolean monitor = Boolean.FALSE;

	private ParametersType parametersType;
	private String parameters;

	private Long installScriptId;
}
