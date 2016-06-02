package lamp.admin.domain.app.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.aether.artifact.Artifact;

@Getter
@Setter
@ToString
public class MavenAppResource implements AppResource {

	private String groupId;

	private String artifactId;

	private String version;

	@Override public String getType() {
		return "maven";
	}
}
