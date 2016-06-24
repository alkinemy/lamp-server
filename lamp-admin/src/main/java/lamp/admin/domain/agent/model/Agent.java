package lamp.admin.domain.agent.model;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_agent")
public class Agent extends AbstractAuditingEntity {

	@Id
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	private String groupId;
	private String artifactId;
	private String version;

	private String protocol;
	private String hostname;
	private String address;
	private int port = -1;

	@Column(name = "app_directory")
	private String appDirectory;

	@Column(name = "secret_key")
	private String secretKey;

	@Column(name = "agent_time", nullable = true)
	private LocalDateTime time;

	private String healthType;
	@Column(name = "health_path")
	private String healthPath;

	private String metricsType;
	@Column(name = "metrics_path")
	private String metricsPath;


	@Transient
	public String getHealthUrl() {
		StringBuilder url = new StringBuilder();
		url.append(getProtocol()).append("://");
		url.append(getAddress()).append(":").append(getPort());
		url.append(getHealthPath());
		return url.toString();
	}

	@Transient
	public String getMetricsUrl() {
		StringBuilder url = new StringBuilder();
		url.append(getProtocol()).append("://");
		url.append(getAddress()).append(":").append(getPort());
		url.append(getMetricsPath());
		return url.toString();
	}

}
