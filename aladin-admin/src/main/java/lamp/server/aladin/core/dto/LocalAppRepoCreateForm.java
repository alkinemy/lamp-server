package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class LocalAppRepoCreateForm extends AppRepoCreateForm {

	@NotEmpty
	private String repositoryPath;

}
