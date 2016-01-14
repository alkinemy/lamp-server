package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UrlAppRepoCreateForm extends AppRepoCreateForm {

	private String baseUrl;
	private String authType;
	private String authUrl;
	private String username;
	private String password;

}
