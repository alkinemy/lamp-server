package lamp.server.aladin.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@DiscriminatorValue(value = AppFileType.Values.LOCAL)
@Table(name = "app_url_repository")
@PrimaryKeyJoinColumn(name = "id")
public class UrlAppRepo extends AppRepo {

	@Column(name = "baseUrl")
	private String baseUrl;

	@Column(name = "repository_auth_type")
	private String authType;
	@Column(name = "repository_auth_url")
	private String authUrl;
	@Column(name = "repository_username")
	private String username;
	@Column(name = "repository_password")
	private String password;


}
