package lamp.admin.domain.host.model.form;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public class HostAgentFile {

	private Resource resource;

	public HostAgentFile(Resource resource) {
		this.resource = resource;
	}

	public File getFile() throws IOException {
		return resource.getFile();
	}

}
