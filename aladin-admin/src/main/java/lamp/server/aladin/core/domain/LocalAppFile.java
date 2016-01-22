package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "app_local_file")
@PrimaryKeyJoinColumn(name = "id")
public class LocalAppFile extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	@Column(name = "repository_id")
	private Long repositoryId;

	@Column(name = "group_id")
	private String groupId;

	@Column(name = "artifact_id")
	private String artifactId;

	@Column(name = "version")
	private String version;

	private String pathname;
	private String filename;

	@Column(name = "deleted", columnDefinition = "TINYINT", nullable = false)
	private Boolean deleted;

}
