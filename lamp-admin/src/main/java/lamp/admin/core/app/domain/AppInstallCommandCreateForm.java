package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AppInstallCommandCreateForm {

	private String name;
	private String description;

	public abstract AppInstallCommandType getType();

}
