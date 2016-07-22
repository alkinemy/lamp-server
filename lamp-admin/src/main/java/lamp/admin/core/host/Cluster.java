package lamp.admin.core.host;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cluster {

	private String id;
	private String name;
	private String description;

	private ClusterType type;

	private String tenantId;

}
