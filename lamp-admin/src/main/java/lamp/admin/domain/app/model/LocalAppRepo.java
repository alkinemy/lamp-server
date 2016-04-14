package lamp.admin.domain.app.model;

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

	private String repositoryPath;

	private Long fileLimitSize;

	private Long fileExpirationTime;

}
