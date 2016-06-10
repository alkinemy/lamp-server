package lamp.admin.domain.resource.repo.model.dto;

import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.domain.resource.repo.model.AppRepoType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AppRepoDto {

	private String id;
	private String name;
	private String description;
	private AppRepoType repositoryType;

	private String etc;

}
