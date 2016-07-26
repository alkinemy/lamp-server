package lamp.admin.domain.host.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "lamp_ssh_key")
public class SshKeyEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	private String name;
	private String description;

	private String username;
	private String privateKey;
	private String passphrase;

}