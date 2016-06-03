package lamp.admin.domain.app.repo.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class LocalAppFileDto {

	private Long id;
	private String name;
	private String description;
	private String repositoryId;
	private String repositoryName;
	private String groupId;
	private String artifactId;
	private String baseVersion;
	private String version;

	private String pathname;
	private String filename;
	private LocalDateTime fileDate;
	private Long fileSize;
}
