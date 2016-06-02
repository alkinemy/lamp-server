package lamp.admin.domain.app.resource.resource;


import lombok.Getter;
import lombok.ToString;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Getter
@ToString
public abstract class FileSystemAppResource extends FileSystemResource implements AppResource {

	private final String filename;
	private final String groupId;
	private final String artifactId;
	private final String version;

	public FileSystemAppResource(File file, String groupId, String artifactId, String version) {
		this(file, file.getName(), groupId, artifactId, version);
	}

	public FileSystemAppResource(File file, String filename, String groupId, String artifactId, String version) {
		super(file);
		this.filename = filename;
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}
}
