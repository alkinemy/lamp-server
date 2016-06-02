package lamp.admin.domain.old.app.model;

import lamp.admin.domain.script.model.ScriptCommand;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppInstallScriptDto {

	private Long id;
	private String templateId;

	private String name;
	private String description;
	private String version;

	private List<ScriptCommand> commands;

}
