package lamp.admin.domain.host.service.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AwsEc2HostsForm {

	private String imageId;
	private String instanceType;
	private String keyName;
	private String subnetId;
	private String securityGroupIds;

	private String userData;

	private int minCount = 1;
	private int maxCount = 1;

}
