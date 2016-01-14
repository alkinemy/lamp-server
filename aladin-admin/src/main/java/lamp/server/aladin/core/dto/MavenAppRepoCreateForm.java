package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MavenAppRepoCreateForm extends AppRepoCreateForm {

	private String url;
	private String username;
	private String password;

	private String proxy;

}
