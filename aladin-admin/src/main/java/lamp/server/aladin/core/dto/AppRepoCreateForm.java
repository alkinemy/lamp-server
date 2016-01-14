package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.AppFileType;
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

	private AppFileType repositoryType;

}
