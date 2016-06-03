package lamp.admin.domain.agent.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TargetServerDto {

	private String id;
	private String name;
	private String description;

	private String hostname;
	private String address;

	private SshAuthType sshAuthType;
	private Long sshKeyId;
	private String sshKeyName;
	private String sshUsername;
	private String sshPassword;

	private Boolean agentInstalled;
	private String agentPath;

	private String agentInstalledBy;
	private LocalDateTime agentInstalledDate;
	private String agentInstallPath;
	private String agentInstallFilename;
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

	private String agentStatus;
}
