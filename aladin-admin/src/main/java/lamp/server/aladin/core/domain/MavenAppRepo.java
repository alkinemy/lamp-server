package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppFileType.Values.MAVEN)
@Table(name = "app_maven_repository")
@PrimaryKeyJoinColumn(name = "id")
public class MavenAppRepo extends AppRepo {

	@Column(name = "repository_url")
	private String url;

	@Column(name = "repository_username")
	private String username;

	@Column(name = "repository_password")
	private String password;

	private String proxy;

}
