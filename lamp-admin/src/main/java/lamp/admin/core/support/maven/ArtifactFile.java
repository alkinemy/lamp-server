package lamp.admin.core.support.maven;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eclipse.aether.artifact.Artifact;

import java.io.File;

@AllArgsConstructor
@Getter
public class ArtifactFile {

	private Artifact artifact;
	private File file;

}
