package lamp.admin.domain.app.repo.model.dto;

import lamp.admin.domain.app.repo.model.AppResourceType;
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
	private AppResourceType repositoryType;

	private String etc;

}
