package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppFileType.Values.MAVEN)
@Table(name = "app_maven_file")
@PrimaryKeyJoinColumn(name = "id")
public class MavenAppFile extends AppFile {

	private MavenAppRepo appRepository;

	@Column(name = "group_id")
	private String groupId;

	@Column(name = "artifact_id")
	private String artifactId;

	@Column(name = "version")
	private String version;

}
