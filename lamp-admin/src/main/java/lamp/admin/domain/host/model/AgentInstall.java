package lamp.admin.domain.host.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AgentInstall {

	private String address;
	private HostCredentials hostCredentials;
	private HostConfiguration hostConfiguration;

	private String agentId;
	private String agentFile;
	private String agentInstallDirectory;
	private String agentInstallFilename;


}
