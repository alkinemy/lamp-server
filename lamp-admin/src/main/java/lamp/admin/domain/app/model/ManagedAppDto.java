package lamp.admin.domain.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class ManagedAppDto {

	private String id;
	private String name;
	private String description;

	private String hostname;
	private String address;

	private String groupId;
	private String artifactId;
	private String artifactName;
	private String version;

	private String appTemplateId;
	private String appTemplateName;

	private String appManagementListener;

	private LocalDateTime registerDate;
	private LocalDateTime installDate;


	private boolean updatable;
}
