package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UrlAppRepoUpdateForm extends AppRepoUpdateForm {

	public UrlAppRepoUpdateForm() {
		setRepositoryType(AppResourceType.URL);
	}

	private String baseUrl;
	private String authType;
	private String authUrl;
	private String username;
	private String password;

}
