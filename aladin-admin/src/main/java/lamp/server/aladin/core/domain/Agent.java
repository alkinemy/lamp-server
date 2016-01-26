package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "lamp_agent")
public class Agent extends AbstractAuditingEntity {

	@Id
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	private String type;
	private String version;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_server_id")
	private TargetServer targetServer;

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

	@Column(name = "health_path")
	private String healthPath;
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
