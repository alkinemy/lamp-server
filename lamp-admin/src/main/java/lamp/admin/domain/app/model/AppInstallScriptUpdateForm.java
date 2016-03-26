package lamp.admin.domain.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppInstallScriptUpdateForm {

	private String name;
	private String description;
	private String version;

	private String commands;

}
