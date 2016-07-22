package lamp.admin.core.host;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AwsCluster extends Cluster {

	private String instanceType;
	private String keyName;
	private String securityGroups;

}
