package lamp.admin.domain.app.model;

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
