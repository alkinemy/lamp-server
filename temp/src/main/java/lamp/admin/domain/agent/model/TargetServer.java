package lamp.admin.domain.agent.model;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lamp.admin.domain.monitoring.model.HealthStatusCode;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.utils.StringUtils;
import lamp.monitoring.core.health.model.MonitoringHealthTarget;
import lamp.monitoring.core.metrics.model.MonitoringMetricsTarget;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "lamp_target_server")
@SecondaryTables({
	@SecondaryTable(name="lamp_watch_target", pkJoinColumns=@PrimaryKeyJoinColumn(name="id")),
	@SecondaryTable(name="lamp_target_server_status", pkJoinColumns=@PrimaryKeyJoinColumn(name="id"))
})
public class TargetServer extends AbstractAuditingEntity implements HealthTarget, MetricsTarget, MonitoringHealthTarget, MonitoringMetricsTarget {

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

	// lamp_watch_target
	@Column(name = "name", table = "lamp_watch_target")
	private String targetName;
	@Column(name = "hostname", table = "lamp_watch_target")
	private String targetHostname;
	@Column(name = "address", table = "lamp_watch_target")
	private String targetAddress;

	@Column(table = "lamp_watch_target")
	private String targetType;

	@Column(table = "lamp_watch_target")
	private String groupId;
	@Column(table = "lamp_watch_target")
	private String artifactId;
	@Column(table = "lamp_watch_target")
	private String version;

	@Column(columnDefinition = "TINYINT", table = "lamp_watch_target")
	private boolean healthMonitoringEnabled = false;
	@Column(columnDefinition = "TINYINT", table = "lamp_watch_target")
	private boolean healthCollectionEnabled = false;
	@Column(table = "lamp_watch_target")
	private String healthType;
	@Column(table = "lamp_watch_target")
	private String healthUrl;
	@Column(table = "lamp_watch_target")
	private String healthExportPrefix;

	@Column(columnDefinition = "TINYINT", table = "lamp_watch_target")
	private boolean metricsMonitoringEnabled = false;
	@Column(columnDefinition = "TINYINT", table = "lamp_watch_target")
	private boolean metricsCollectionEnabled = false;
	@Column(table = "lamp_watch_target")
	private String metricsType;
	@Column(table = "lamp_watch_target")
	private String metricsUrl;
	@Column(table = "lamp_watch_target")
	private String metricsExportPrefix;

	@Column(name = "tags", table = "lamp_watch_target")
	private String tagsJsonString;

	// lamp_target_server_status
	@Column(name = "agent_status", table = "lamp_target_server_status")
	private String agentStatus = HealthStatusCode.UNKNOWN.name();

	@Column(name = "agent_status_date", table = "lamp_target_server_status")
	private LocalDateTime agentStatusDate;

	@Column(name = "created_by", table = "lamp_watch_target", nullable = false, length = 100, updatable = false)
	public String watchTargetCreatedBy = "TargetServer";

	@Column(name = "created_date", table = "lamp_watch_target", nullable = false, updatable = false)
	private LocalDateTime watchTargetCreatedDate = LocalDateTime.now();

	@Column(name = "last_modified_by", table = "lamp_watch_target", length = 100)
	private String watchTargetLastModifiedBy = "TargetServer";

	@Column(name = "last_modified_date", table = "lamp_watch_target")
	private LocalDateTime watchTargetLastModifiedDate = LocalDateTime.now();

	@Override public Map<String, String> getTags() {
		if (StringUtils.isNotBlank(tagsJsonString)) {
			return JsonUtils.parse(tagsJsonString, LinkedHashMap.class);
		}
		return Collections.emptyMap();
	}
}
