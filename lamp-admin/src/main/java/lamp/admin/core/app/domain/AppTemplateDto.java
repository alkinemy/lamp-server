package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppTemplateDto {

	private Long id;
	private String name;
	private String description;

	private AppResourceType resourceType;
	private Long repositoryId;
	private String repositoryName;

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
	private String appFilename;

	private boolean monitor;

	private String commands;

	private ParametersType parametersType;
	private String parameters;

}
