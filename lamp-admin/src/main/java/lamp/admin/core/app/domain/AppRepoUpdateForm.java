package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AppRepoUpdateForm {

	@NotNull
	private String id;

	@NotEmpty
	private String name;

	private String description;

	private AppResourceType repositoryType;

}
