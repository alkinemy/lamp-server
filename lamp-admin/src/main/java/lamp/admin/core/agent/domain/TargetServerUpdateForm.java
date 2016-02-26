package lamp.admin.core.agent.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class TargetServerUpdateForm {

	@NotNull
	private Long id;
	@NotEmpty
	private String name;
	private String description;

	@NotEmpty
	private String hostname;
	@NotEmpty
	private String address;

	private int sshPort = 22;

	private SshAuthType authType;
	private Long sshKeyId;
	private String privateKey;

	private String username;
	private String password;

	private Boolean agentInstalled;
	@NotEmpty
	private String agentInstallPath;
	private String agentPidFile;
	private String agentStartCommandLine;
	private String agentStopCommandLine;

	private Boolean agentMonitor;
	private Long agentMonitorInterval;

	private Boolean agentHealthCheckEnabled = Boolean.FALSE;
	private String agentHealthType;
	private String agentHealthUrl;

	private Boolean agentMetricsCollectEnabled = Boolean.FALSE;
	private String agentMetricsType;
	private String agentMetricsUrl;

}
