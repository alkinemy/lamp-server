package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "app_local_file")
public class LocalAppFile extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	private Long repositoryId;

	private String groupId;

	private String artifactId;

	private String baseVersion;
	private String version;

	private String pathname;
	private String filename;

	private Long fileSize;
	private String contentType;

	@Column(name = "deleted", columnDefinition = "TINYINT", nullable = false)
	private Boolean deleted;


	@Transient
	public boolean isSnapshot() {
		return baseVersion != null && baseVersion.endsWith("-SNAPSHOT");
	}

}
