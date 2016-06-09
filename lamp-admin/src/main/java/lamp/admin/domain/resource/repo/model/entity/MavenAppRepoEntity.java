package lamp.admin.domain.resource.repo.model.entity;

import lamp.admin.core.app.simple.resource.AppResourceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppResourceType.Values.MAVEN)
@Table(name = "lamp_app_maven_repository")
@PrimaryKeyJoinColumn(name = "id")
public class MavenAppRepoEntity extends AppRepoEntity {

	@Column(name = "repository_url")
	private String url;

	@Column(name = "repository_username")
	private String username;

	@Column(name = "repository_password")
	private String password;

	private String proxy;

}
