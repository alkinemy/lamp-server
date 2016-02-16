package lamp.admin.core.support.agent.model;

import lamp.admin.core.app.domain.AppProcessType;
import lamp.admin.core.app.domain.ParametersType;
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
	private String logDirectory;
	private String pidFile;
	private String stdOutFile;
	private String stdErrFile;
	private String commandShell;
	private String startCommandLine;
	private String stopCommandLine;

	private boolean preInstalled;
	private Resource installFile;
	private String filename;
	private boolean monitor;

	private String commands;

	private ParametersType parametersType;
	private String parameters;

}
