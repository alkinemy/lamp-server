package lamp.server.aladin.core.domain;

import lamp.server.aladin.common.domain.AbstractAuditingEntity;
import lamp.server.aladin.core.DownloadableFile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lamp_agent_jar")
public class AgentJar extends AbstractAuditingEntity implements DownloadableFile {

	@Id
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	private String version;

	@Column(name = "repository_type")
	private String repositoryType;

	@Enumerated(EnumType.STRING)
	@Column(name = "repository_url")
	private FileRepositoryType repositoryUrl;

	@Column(name = "repository_username")
	private String username;

	@Column(name = "repository_password")
	private String password;

	@Column(name = "group_id")
	private String groupId;

	@Column(name = "artifact_id")
	private String artifactId;


	private String startCmd;
}
