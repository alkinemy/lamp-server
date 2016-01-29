package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_target_server")
@SecondaryTable(name="lamp_target_server_status",
		pkJoinColumns=@PrimaryKeyJoinColumn(name="id"))
public class TargetServer extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 200)
	private String name;

	@Column(length = 200)
	private String description;

	@Column(length = 100, unique = true, nullable = false, updatable = false)
	private String hostname;

	@Column(length = 100, nullable = false)
	private String address;

	@Column(name = "ssh_port")
	private int sshPort = 22;

	@Column(name = "auth_type", length = 100, nullable = false)
	private String authType;

	@Column(length = 100)
	private String username;

	@Column(name = "encrypted_password", length = 100)
	private String password;

	@Column(name = "agent_installed", columnDefinition = "TINYINT")
	private Boolean agentInstalled = Boolean.FALSE;

	@Column(name = "agent_installed_by")
	private String agentInstalledBy;

	@Column(name = "agent_installed_date")
	private LocalDateTime agentInstalledDate;

	@Column(name = "agent_install_path")
	private String agentInstallPath;

	@Column(name = "agent_install_filename")
	private String agentInstallFilename;

	@Column(name = "agent_start_command_line")
	private String agentStartCommandLine;

	@Column(name = "agent_stop_command_line")
	private String agentStopCommandLine;

	@Column(name = "agent_health_url")
	private String agentHealthUrl;

	@Column(name = "agent_monitor", columnDefinition = "TINYINT")
	private Boolean agentMonitor;

	@Column(name = "agent_monitor_interval")
	private Long agentMonitorInterval;

	@Column(name = "agent_status", table = "lamp_target_server_status")
	private String agentStatus = HealthStatusCode.UNKNOWN.name();

	@Column(name = "agent_status_date", table = "lamp_target_server_status")
	private LocalDateTime agentStatusDate;

}
