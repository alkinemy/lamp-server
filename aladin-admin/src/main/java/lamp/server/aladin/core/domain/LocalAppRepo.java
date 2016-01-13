package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppFileType.Values.LOCAL)
@Table(name = "app_local_repository")
@PrimaryKeyJoinColumn(name = "id")
public class LocalAppRepo extends AppRepo {

	@Column(name = "repository_path")
	private String repositoryPath;

}
