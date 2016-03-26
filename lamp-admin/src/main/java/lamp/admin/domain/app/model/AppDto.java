package lamp.admin.domain.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppDto {

	private String id;
	private String name;
	private String description;

	private String groupId;
	private String artifactId;
	private String artifactName;
	private String version;

	private String processType;
	private String pid;
	private String status;
	private String correctStatus;

	private boolean isMonitor;

}
