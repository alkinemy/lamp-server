package lamp.admin.domain.app.resource.resource;

import org.springframework.core.io.Resource;

public interface AppResource extends Resource {

	String getGroupId();

	String getArtifactId();

	String getVersion();

}
