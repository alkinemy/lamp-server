package lamp.watcher.support.jpa.domain;

import lamp.common.monitoring.MonitoringTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "lamp_monitoring_target")
public class JpaMonitoringTarget implements MonitoringTarget {

	@Id
	private String id;
	private String name;

	private String hostname;
	private String address;

	private String agentId;

	private String groupId;
	private String artifactId;
	private String version;

	@Column(columnDefinition = "TINYINT")
	private Boolean healthMonitoringEnabled;
	@Column(columnDefinition = "TINYINT")
	private Boolean healthCollectionEnabled;
	private String healthType;
	private String healthUrl;
	private String healthExportPrefix;

	@Column(columnDefinition = "TINYINT")
	private Boolean metricsMonitoringEnabled;
	@Column(columnDefinition = "TINYINT")
	private Boolean metricsCollectionEnabled;
	private String metricsType;
	private String metricsUrl;
	private String metricsExportPrefix;

}
