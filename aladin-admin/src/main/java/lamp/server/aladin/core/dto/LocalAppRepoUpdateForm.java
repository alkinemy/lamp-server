package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.AppResourceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class LocalAppRepoUpdateForm extends AppRepoUpdateForm {

	public LocalAppRepoUpdateForm() {
		setRepositoryType(AppResourceType.LOCAL);
	}

	@NotEmpty
	private String repositoryPath;

}
