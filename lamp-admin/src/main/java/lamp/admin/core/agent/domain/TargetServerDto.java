package lamp.admin.core.agent.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TargetServerDto {

	private Long id;
	private String name;
	private String description;

	private String hostname;
	private String address;

	private SshAuthType authType;
	private String username;
	private String password;

	private Boolean agentInstalled;
	private String agentPath;

	private String agentInstalledBy;
	private LocalDateTime agentInstalledDate;
	private String agentInstallPath;
	private String agentInstallFilename;
	private String agentPidFile;
	private String agentStartCommandLine;
	private String agentStopCommandLine;

	private String agentHealthUrl;

	private Boolean agentMonitor;
	private Long agentMonitorInterval;

	private String agentStatus;
}
