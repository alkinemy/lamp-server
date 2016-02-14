package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppResourceType.Values.LOCAL)
@Table(name = "lamp_app_local_repository")
@PrimaryKeyJoinColumn(name = "id")
public class LocalAppRepo extends AppRepo {

	@Column(name = "repository_path")
	private String repositoryPath;

}
