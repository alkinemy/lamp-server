package lamp.admin.core.host;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Host extends HostStatus {

	private String id;
	private String description;

	private String name;
	private String address;

	private String agentGroupId;
	private String agentArtifactId;
	private String agentVersion;

	private String agentAccessKey;
	private String agentSecretKey;

	private String clusterId;
	private String clusterName;
	private String rack;

	private String tenantId;

	private List<String> roles;

	private Map<String, Object> parameters;
	private Map<String, String> tags;

	//
	private String agentInstallDirectory;
	private String agentInstallFilename;
	private String agentFile;

}
