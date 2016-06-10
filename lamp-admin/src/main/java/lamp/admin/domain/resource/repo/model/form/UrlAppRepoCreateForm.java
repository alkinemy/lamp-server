package lamp.admin.domain.resource.repo.model.form;

import lamp.admin.core.app.simple.resource.AppResourceType;

import lamp.admin.domain.resource.repo.model.AppRepoType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UrlAppRepoCreateForm extends AppRepoCreateForm {

	public UrlAppRepoCreateForm() {
		setRepositoryType(AppRepoType.URL);
	}

	private String baseUrl;
	private String authType;
	private String authUrl;
	private String username;
	private String password;

}
