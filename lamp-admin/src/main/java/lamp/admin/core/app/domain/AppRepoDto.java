package lamp.admin.core.app.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AppRepoDto {

	private Long id;
	private String name;
	private String description;
	private AppResourceType repositoryType;

	private String etc;

}
