package lamp.admin.domain.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class LocalAppRepoCreateForm extends AppRepoCreateForm {

	public LocalAppRepoCreateForm() {
		setRepositoryType(AppResourceType.LOCAL);
	}

	@NotEmpty
	private String repositoryPath;

}
