package lamp.server.aladin.core.support.agent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.io.Resource;

@Getter
@Setter
@ToString
public class AgentAppUpdateFileForm {

	private String id;

	private String groupId;
	private String artifactId;
	private String artifactName;
	private String version;

	private Resource installFile;
	private String commands;

}
