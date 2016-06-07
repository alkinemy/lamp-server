package lamp.admin.domain.host.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_host_auth")
public class HostAuthEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	private String name;
	private String description;

	private String sshUsername;
	@Column(columnDefinition = "TINYINT")
	private boolean sshUsePassword;

	private String sshPassword;

	private String sshPrivateKey;
	private String sshPasspharse;

	private Integer sshPort;

}