package lamp.admin.domain.resource.repo.model.form;

import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.domain.resource.repo.model.AppRepoType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class LocalAppRepoCreateForm extends AppRepoCreateForm {

	public LocalAppRepoCreateForm() {
		setRepositoryType(AppRepoType.LOCAL);
	}

	@NotEmpty
	private String repositoryPath;

}
