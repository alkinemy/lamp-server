package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "app_file")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "file_type", discriminatorType = DiscriminatorType.STRING)
public class AppFile extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "group_id")
	private String groupId;

	@Column(name = "name")
	private String name;

	@Column(name = "version")
	private String version;
}
