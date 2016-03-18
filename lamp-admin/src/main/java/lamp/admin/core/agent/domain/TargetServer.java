package lamp.admin.core.agent.domain;

import lamp.admin.core.base.domain.AbstractAuditingEntity;
import lamp.admin.core.monitoring.domain.HealthStatusCode;
import lamp.common.monitoring.MonitoringTarget;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "lamp_target_server")
@SecondaryTables({
	@SecondaryTable(name="lamp_collection_target", pkJoinColumns=@PrimaryKeyJoinColumn(name="id")),
	@SecondaryTable(name="lamp_target_server_status", pkJoinColumns=@PrimaryKeyJoinColumn(name="id"))
})
public class TargetServer extends AbstractAuditingEntity implements MonitoringTarget {

	@Id
	private String id;

	private String name;
	private String description;

	private String hostname;
	private String address;

	private int sshPort = 22;
	@Enumerated(EnumType.STRING)
	private SshAuthType sshAuthType = SshAuthType.PASSWORD;
	private Long sshKeyId;
	private String sshKey;
	private String sshUsername;
	private String sshPassword;


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

	// lamp_collection_target
	@Column(name = "name", table = "lamp_collection_target")
	private String targetName;
	@Column(name = "hostname", table = "lamp_collection_target")
	private String targetHostname;
	@Column(name = "address", table = "lamp_collection_target")
	private String targetAddress;

	@Column(name = "groupId", table = "lamp_collection_target")
	private String groupId;
	@Column(name = "artifactId", table = "lamp_collection_target")
	private String artifactId;
	@Column(name = "version", table = "lamp_collection_target")
	private String version;

	@Column(columnDefinition = "TINYINT", table = "lamp_collection_target")
	private Boolean healthMonitoringEnabled = Boolean.FALSE;
	@Column(columnDefinition = "TINYINT", table = "lamp_collection_target")
	private Boolean healthCollectionEnabled = Boolean.FALSE;
	@Column(table = "lamp_collection_target")
	private String healthType;
	@Column(table = "lamp_collection_target")
	private String healthUrl;
	@Column(table = "lamp_collection_target")
	private String healthExportPrefix;

	@Column(columnDefinition = "TINYINT", table = "lamp_collection_target")
	private Boolean metricsMonitoringEnabled = Boolean.FALSE;
	@Column(columnDefinition = "TINYINT", table = "lamp_collection_target")
	private Boolean metricsCollectionEnabled = Boolean.FALSE;
	@Column(table = "lamp_collection_target")
	private String metricsType;
	@Column(table = "lamp_collection_target")
	private String metricsUrl;
	@Column(table = "lamp_collection_target")
	private String metricsExportPrefix;

	// lamp_target_server_status
	@Column(name = "agent_status", table = "lamp_target_server_status")
	private String agentStatus = HealthStatusCode.UNKNOWN.name();

	@Column(name = "agent_status_date", table = "lamp_target_server_status")
	private LocalDateTime agentStatusDate;

	@Override public Map<String, String> getTags() {
		return null;
	}
}
