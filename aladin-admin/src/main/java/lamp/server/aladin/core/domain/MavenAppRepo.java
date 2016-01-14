package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
