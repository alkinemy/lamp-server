package lamp.admin.domain.resource.repo.model.form;

import lamp.admin.domain.resource.repo.model.AppRepoType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MavenAppRepoUpdateForm extends AppRepoUpdateForm {

	public MavenAppRepoUpdateForm() {
		setRepositoryType(AppRepoType.MAVEN);
	}

	private String url;
	private String username;
	private String password;

	private String proxy;

}
