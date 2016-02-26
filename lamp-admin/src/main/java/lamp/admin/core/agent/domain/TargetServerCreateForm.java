package lamp.admin.core.agent.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;

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

	private SshAuthType authType = SshAuthType.PASSWORD;
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

	private Boolean agentMonitor = Boolean.FALSE;
	private Long agentMonitorInterval;

	private Boolean agentHealthCheckEnabled = Boolean.FALSE;
	private String agentHealthType;
	private String agentHealthUrl;

	private Boolean agentMetricsCollectEnabled = Boolean.FALSE;
	private String agentMetricsType;
	private String agentMetricsUrl;

}
