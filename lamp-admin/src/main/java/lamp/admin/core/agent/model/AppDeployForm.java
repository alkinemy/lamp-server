package lamp.admin.core.agent.model;

import lamp.admin.core.app.base.App;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppDeployForm {

	private String id; // instanceId
	private String name;
	private String description;

	private App app;


}
