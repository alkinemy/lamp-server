package lamp.admin.core.host;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AwsEc2Host extends Host {

	private String region;

	private String instanceId;
	private String imageId;

	private String privateDnsName;
	private String privateIpAddress;

	private String publicDnsName;
	private String publicIpAddress;

}
