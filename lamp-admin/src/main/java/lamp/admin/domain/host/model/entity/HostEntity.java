package lamp.admin.domain.host.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_host")
@SecondaryTables({
	@SecondaryTable(name="lamp_host_status", pkJoinColumns=@PrimaryKeyJoinColumn(name="id"))
})
public class HostEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	private String name;
	private String description;

	private String address;

	private String clusterId;
	private String rack;

	private String data;

	@Column(columnDefinition = "TINYINT")
	private boolean healthMonitoringEnabled = false;
	@Column(columnDefinition = "TINYINT")
	private boolean healthCollectionEnabled = false;

	private String healthType;
	private String healthPath;

	@Column(columnDefinition = "TINYINT")
	private boolean metricsMonitoringEnabled = false;
	@Column(columnDefinition = "TINYINT")
	private boolean metricsCollectionEnabled = false;
	private String metricsType;
	private String metricsPath;

	// Agent
	private String authId;

	@Column(columnDefinition = "TINYINT")
	private boolean agentInstalled = false;
	private String agentInstalledBy;
	private LocalDateTime agentInstalledDate;
	private String agentFile;
	private String agentInstallDirectory;
	private String agentInstallFilename;
	private String agentGroupId;
	private String agentArtifactId;
	private String agentVersion;
	private String agentPidFile;
	private String agentStartCommandLine;
	private String agentStopCommandLine;

	// 로드 평균
	@Column(table = "lamp_host_status")
	private double cpuUser;
	@Column(table = "lamp_host_status")
	private double cpuNice;
	@Column(table = "lamp_host_status")
	private double cpuSys;

	// 디스크 사용량
	@Column(table = "lamp_host_status")
	private long diskTotal;
	@Column(table = "lamp_host_status")
	private long diskUsed;
	@Column(table = "lamp_host_status")
	private long diskFree;

	// 물리적 메모리
	@Column(table = "lamp_host_status")
	private long memTotal;
	@Column(table = "lamp_host_status")
	private long memUsed;
	@Column(table = "lamp_host_status")
	private long memFree;

	// 스왑 공간
	@Column(table = "lamp_host_status")
	private long swapTotal;
	@Column(table = "lamp_host_status")
	private long swapUsed;
	@Column(table = "lamp_host_status")
	private long swapFree;

}