package lamp.admin.domain.support.resource;

import lombok.ToString;
import org.eclipse.aether.artifact.Artifact;

@ToString
public class MavenAppResource extends FileSystemAppResource {

	public MavenAppResource(Artifact artifact) {
		super(artifact.getFile(), artifact.getGroupId(), artifact.getArtifactId(), artifact.getBaseVersion());
	}

}
