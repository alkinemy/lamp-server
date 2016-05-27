package lamp.admin.domain.docker.model;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
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
@Table(name = "docker_app")
public class DockerAppEntity extends AbstractAuditingEntity {

	@Id
	private String id;

	private String groupId;

	private String data;

}
