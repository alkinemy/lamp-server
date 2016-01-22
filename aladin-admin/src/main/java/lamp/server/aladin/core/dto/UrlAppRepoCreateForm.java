package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.AppResourceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UrlAppRepoCreateForm extends AppRepoCreateForm {

	public UrlAppRepoCreateForm() {
		setRepositoryType(AppResourceType.URL);
	}

	private String baseUrl;
	private String authType;
	private String authUrl;
	private String username;
	private String password;

}
