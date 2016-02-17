package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppInstallScriptDto {

	private Long id;
	private Long templateId;

	private String name;
	private String description;
	private String version;

	private List<AppInstallCommand> commands;

}
