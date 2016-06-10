package lamp.admin.domain.resource.repo.model.entity;

import lamp.admin.domain.resource.repo.model.AppRepoType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = AppRepoType.Values.LOCAL)
@Table(name = "lamp_app_local_repository")
@PrimaryKeyJoinColumn(name = "id")
public class LocalAppRepoEntity extends AppRepoEntity {

	private String repositoryPath;

	private Long fileLimitSize;

	private Long fileExpirationTime;

}
