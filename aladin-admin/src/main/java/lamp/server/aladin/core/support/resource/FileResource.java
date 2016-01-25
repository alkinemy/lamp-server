package lamp.server.aladin.core.support.resource;

import org.springframework.core.io.FileSystemResource;

import java.io.File;

public class FileResource extends FileSystemResource  {
	private final String filename;

	public FileResource(File file, String filename) {
		super(file);
		this.filename = filename;
	}

	@Override
	public String getFilename() {
		return this.filename;
	}

}
