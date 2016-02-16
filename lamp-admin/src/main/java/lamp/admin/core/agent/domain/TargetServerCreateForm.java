package lamp.admin.core.agent.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class TargetServerCreateForm {

	@NotEmpty
	private String name;
	private String description;

	@NotEmpty
	private String hostname;
	@NotEmpty
	private String address;

	private SshAuthType authType;
	private String username;
	private String password;
	private String privateKey;

	private Boolean agentInstalled;
	@NotEmpty
	private String agentInstallPath;
	private String agentPidFile;
	private String agentStartCommandLine;
	private String agentStopCommandLine;

	private Boolean agentMonitor = Boolean.FALSE;
	private Long agentMonitorInterval;

}
