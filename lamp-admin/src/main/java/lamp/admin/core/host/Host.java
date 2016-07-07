package lamp.admin.core.host;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Host extends HostStatus {

	private String id;
	private String description;

	private String name;
	private String address;

	private String agentVersion;
	private String agentAccessKey;
	private String agentSecretKey;

	private String clusterId;
	private String rack;

	private String tenantId;

	private List<String> roles;

	private Map<String, String> tags;

}
