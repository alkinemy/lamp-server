package lamp.server.aladin.core.domain;

import lamp.server.aladin.common.domain.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

	@OneToOne
	@JoinColumn(name = "target_server_id")
	private TargetServer targetServer;

	private String protocol;
	private String hostname;
	private String address;
	private int port = -1;

	@Column(name = "secret_key")
	private String secretKey;

	@Column(name = "agent_time", nullable = true)
	private Date time;

	@Column(name = "health_path")
	private String healthPath;
	@Column(name = "metrics_path")
	private String metricsPath;

}
