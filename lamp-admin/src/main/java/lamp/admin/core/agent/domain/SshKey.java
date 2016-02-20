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
@Table(name = "lamp_ssh_key")
public class SshKey extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 200)
	private String name;

	@Column(length = 200)
	private String description;

	private String privateKey;

	@Column(length = 100)
	private String username;

	@Column(name = "encrypted_password", length = 100)
	private String password;

}
