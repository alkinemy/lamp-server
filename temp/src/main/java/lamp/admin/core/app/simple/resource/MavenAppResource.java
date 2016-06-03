package lamp.admin.core.app.simple.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MavenAppResource implements AppResource {

	private String repositoryId;

	private String groupId;

	private String artifactId;

	private String version;

}
