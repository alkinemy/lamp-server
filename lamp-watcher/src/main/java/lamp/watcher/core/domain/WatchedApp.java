package lamp.watcher.core.domain;

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
@Table(name = "lamp_watched_app")
public class WatchedApp {

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
	private Boolean monitoringEnabled;

	@Column(columnDefinition = "TINYINT")
	private Boolean healthMonitoringEnabled;
	@Column(columnDefinition = "TINYINT")
	private Boolean healthCollectionEnabled;
	private String healthType;
	private String healthUrl;

	@Column(columnDefinition = "TINYINT")
	private Boolean metricsMonitoringEnabled;
	@Column(columnDefinition = "TINYINT")
	private Boolean metricsCollectionEnabled;
	private String metricsType;
	private String metricsUrl;

}
