package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppFileType.Values.LOCAL)
@Table(name = "app_url_repository")
@PrimaryKeyJoinColumn(name = "id")
public class UrlAppRepo extends AppRepo {

	@Column(name = "repository_username")
	private String username;
	@Column(name = "repository_password")
	private String password;
}
