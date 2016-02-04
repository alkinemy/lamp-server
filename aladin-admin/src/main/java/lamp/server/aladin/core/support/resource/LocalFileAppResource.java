package lamp.server.aladin.core.support.resource;

import java.io.File;

public class LocalFileAppResource extends FileSystemAppResource {

	public LocalFileAppResource(File file, String filename, String groupId, String artifactId, String version) {
		super(file, filename, groupId, artifactId, version);
	}

}
