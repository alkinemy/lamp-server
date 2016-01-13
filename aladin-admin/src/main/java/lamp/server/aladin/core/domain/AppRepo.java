package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "app_repository")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "repository_type", discriminatorType = DiscriminatorType.STRING)
public class AppRepo extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

}
