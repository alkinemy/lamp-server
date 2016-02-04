package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocalAppFileDto {

	private Long id;
	private Long repositoryId;
	private String groupId;
	private String artifactId;
	private String baseVersion;
	private String version;

	private String pathname;
	private String filename;

}
