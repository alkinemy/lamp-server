package lamp.admin.domain.resource.repo.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_app_local_file")
public class LocalAppFileEntity extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	private String repositoryId;

	private String groupId;

	private String artifactId;

	private String baseVersion;
	private String version;

	private String pathname;
	private String filename;

	private Long fileSize;
	private LocalDateTime fileDate;
	private String contentType;

	@Column(name = "deleted", columnDefinition = "TINYINT", nullable = false)
	private Boolean deleted;


	@Transient
	public boolean isSnapshot() {
		return baseVersion != null && baseVersion.endsWith("-SNAPSHOT");
	}

}
