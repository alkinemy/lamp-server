package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.AppResourceType;
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
	private Long id;

	@NotEmpty
	private String name;

	private String description;

	private AppResourceType repositoryType;

}
