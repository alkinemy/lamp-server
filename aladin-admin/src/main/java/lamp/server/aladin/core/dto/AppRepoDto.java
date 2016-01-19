package lamp.server.aladin.core.dto;

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
	private String repositoryType;

}
