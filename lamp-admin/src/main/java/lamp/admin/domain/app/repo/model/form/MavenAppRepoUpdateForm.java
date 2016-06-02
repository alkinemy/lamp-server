package lamp.admin.domain.app.repo.model.form;

import lamp.admin.domain.app.repo.model.AppResourceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MavenAppRepoUpdateForm extends AppRepoUpdateForm {

	public MavenAppRepoUpdateForm() {
		setRepositoryType(AppResourceType.MAVEN);
	}

	private String url;
	private String username;
	private String password;

	private String proxy;

}
