package lamp.admin.core.app.domain;

import lamp.admin.core.script.domain.ScriptCommandDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AppInstallScriptUpdateForm {

	private String name;
	private String description;
	private String version;

	private String commands;

}
