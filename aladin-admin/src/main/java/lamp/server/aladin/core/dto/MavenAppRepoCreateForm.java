package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.AppResourceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MavenAppRepoCreateForm extends AppRepoCreateForm {

	public MavenAppRepoCreateForm() {
		setRepositoryType(AppResourceType.MAVEN);
	}

	private String url;
	private String username;
	private String password;

	private String proxy;

}
