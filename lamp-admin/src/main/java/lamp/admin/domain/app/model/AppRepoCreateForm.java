package lamp.admin.domain.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class AppRepoCreateForm {

	@NotEmpty
	private String name;

	private String description;

	private AppResourceType repositoryType;

}
