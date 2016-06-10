package lamp.admin.domain.resource.repo.model.entity;

import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lamp.admin.domain.resource.repo.model.AppRepoType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lamp_app_repository")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "repository_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AppRepoEntity extends AbstractAuditingEntity {

	@Id
	private String id;

	private String name;

	private String description;

	@Column(name = "repository_type", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private AppRepoType repositoryType;

	@Column(name = "deleted", columnDefinition = "TINYINT", nullable = false)
	private Boolean deleted;

}
