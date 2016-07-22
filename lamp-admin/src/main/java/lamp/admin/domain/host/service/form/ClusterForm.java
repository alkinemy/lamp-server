package lamp.admin.domain.host.service.form;

import lamp.admin.core.host.ClusterType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClusterForm {

	private String id;
	private String name;
	private String description;

	private ClusterType type = ClusterType.DEFAULT;

	private String tenantId;

	// AWS
	private String instanceType;
	private String keyName;
	private String securityGroups;

}
