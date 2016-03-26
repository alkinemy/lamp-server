package lamp.admin.domain.agent.model;

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
	private String id;
	@NotEmpty
	private String name;
	private String description;

	@NotEmpty
	private String hostname;
	@NotEmpty
	private String address;

	private int sshPort = 22;
	private SshAuthType sshAuthType = SshAuthType.PASSWORD;
	private Long sshKeyId;
	private String sshKey;
	private String sshUsername;
	private String sshPassword;

	private Boolean agentInstalled;
	@NotEmpty
	private String agentInstallPath;
	private String agentPidFile;
	private String agentStartCommandLine;
	private String agentStopCommandLine;

	private Boolean healthMonitoringEnabled;
	private Boolean healthCollectionEnabled;
	private String healthType;
	private String healthUrl;
	private String healthExportPrefix;

	private Boolean metricsMonitoringEnabled;
	private Boolean metricsCollectionEnabled;
	private String metricsType;
	private String metricsUrl;
	private String metricsExportPrefix;

}
