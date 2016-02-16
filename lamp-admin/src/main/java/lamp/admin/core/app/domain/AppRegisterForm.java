package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AppRegisterForm {

	private String id;
	private String name;
	private String description;

	@NotNull
	private Long templateId;
	private String version;

	private Boolean monitor;

	private ParametersType parametersType;
	private String parameters;

}
