package lamp.server.aladin.core.domain;

import org.springframework.core.io.Resource;

public interface AppResource extends Resource {

	String getGroupId();

	String getArtifactId();

	String getVersion();

}
