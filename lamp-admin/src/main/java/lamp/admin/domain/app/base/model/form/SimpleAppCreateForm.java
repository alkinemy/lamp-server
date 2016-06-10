package lamp.admin.domain.app.base.model.form;

import lamp.admin.core.app.simple.AppProcessType;
import lamp.admin.core.app.simple.resource.AppResourceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SimpleAppCreateForm {

	private String id;
	private String name;
	private String description;

	@NotNull
	private AppResourceType resourceType = AppResourceType.ARTIFACT;

	private String repositoryId;
	private String groupId;
	private String artifactId;
	private String version;

	private String resourceUrl;

	@NotNull
	private AppProcessType processType = AppProcessType.DAEMON;
	private String appDirectory;
	private String workDirectory;
	private String logDirectory;

	private String pidFile;
	private String ptql;
	private String stdOutFile;
	private String stdErrFile;

	private String commandShell;
	private String startCommandLine;
	private String stopCommandLine;

	private String appFilename;

	private boolean monitor;

	private String commands;

	private ParametersType parametersType;
	private String parameters;

}
