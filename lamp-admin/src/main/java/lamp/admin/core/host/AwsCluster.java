package lamp.admin.core.host;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AwsCluster extends Cluster {

	private String accessKeyId;
	private String secretAccessKey;

	private String ec2Endpoint = "ec2.ap-northeast-2.amazonaws.com";

	private String imageId;
	private String instanceType;
	private String keyName;

	private String subnetId;
	private String securityGroupIds;

	private String userData;
}
