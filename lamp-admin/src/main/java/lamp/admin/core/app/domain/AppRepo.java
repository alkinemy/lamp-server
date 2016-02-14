package lamp.admin.core.app.domain;

import lamp.admin.core.base.domain.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lamp_app_repository")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "repository_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AppRepo extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	@Column(name = "repository_type", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private AppResourceType repositoryType;

	@Column(name = "deleted", columnDefinition = "TINYINT", nullable = false)
	private Boolean deleted;

}
