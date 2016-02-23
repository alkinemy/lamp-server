package lamp.admin.core.agent.domain;

import lamp.admin.core.base.domain.AbstractAuditingEntity;
import lamp.admin.core.monitoring.domain.HealthStatusCode;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "auth_type", length = 100, nullable = false)
	private SshAuthType authType;

	@Column(length = 100)
	private String username;

	@Column(name = "encrypted_password", length = 100)
	private String password;

	private String privateKey;

	@Column(columnDefinition = "TINYINT")
	private Boolean agentInstalled = Boolean.FALSE;

	private String agentInstalledBy;

	private LocalDateTime agentInstalledDate;

	private String agentInstallPath;

	private String agentInstallFilename;

	private String agentGroupId;
	private String agentArtifactId;
	private String agentVersion;

	private String agentPidFile;

	private String agentStartCommandLine;

	private String agentStopCommandLine;

	private String agentHealthUrl;

	@Column(columnDefinition = "TINYINT")
	private Boolean agentMonitor;

	private Long agentMonitorInterval;

	@Column(name = "agent_status", table = "lamp_target_server_status")
	private String agentStatus = HealthStatusCode.UNKNOWN.name();

	@Column(name = "agent_status_date", table = "lamp_target_server_status")
	private LocalDateTime agentStatusDate;

}
