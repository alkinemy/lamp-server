package lamp.admin.domain.agent.model;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
