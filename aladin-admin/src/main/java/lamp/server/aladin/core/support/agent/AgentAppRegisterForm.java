package lamp.server.aladin.core.support.agent;

import lamp.server.aladin.core.domain.AppProcessType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.io.Resource;

@Getter
@Setter
@ToString
public class AgentAppRegisterForm {

	private String id;
	private String name;
	private String description;

	private String groupId;
	private String artifactId;
	private String artifactName;
	private String version;
	private AppProcessType processType;
	private String appDirectory;
	private String workDirectory;
	private String pidFile;
	private String commandShell;
	private String startCommandLine;
	private String stopCommandLine;

	private boolean preInstalled;
	private Resource installFile;
	private String filename;
	private boolean monitor;

	private String commands;

}
