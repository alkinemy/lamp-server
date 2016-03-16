package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ManagedAppRegisterForm {

	private String agentId;

	private String id;
	private String name;
	private String description;

	private String templateId;

	private String groupId;
	private String artifactId;
	private String artifactName;
	private String version;

	private String processType;

	private Boolean monitor;

}
