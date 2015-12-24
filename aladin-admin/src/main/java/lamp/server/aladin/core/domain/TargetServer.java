package lamp.server.aladin.core.domain;

import lamp.server.aladin.common.domain.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lamp_target_server")
public class TargetServer extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 200)
	private String name;

	@Column(length = 200)
	private String description;

	@Column(length = 100, unique = true, nullable = false)
	private String hostname;

	@Column(length = 100, nullable = false)
	private String address;

	@Column(name = "auth_type", length = 100, nullable = false)
	private String authType;

	@Column(length = 100)
	private String username;

	@Column(name = "encrypted_password", length = 100)
	private String password;

	@Column(name = "agent_installed", columnDefinition = "TINYINT")
	private Boolean agentInstalled;

	@Column(name = "monitor", columnDefinition = "TINYINT")
	private Boolean monitor;

	@Column(name = "monitor_interval")
	private Long monitorInterval;

	@Column(name = "agent_path")
	private String agentPath;

	@OneToOne(mappedBy = "targetServer", fetch = FetchType.LAZY)
	private Agent agent;

}
