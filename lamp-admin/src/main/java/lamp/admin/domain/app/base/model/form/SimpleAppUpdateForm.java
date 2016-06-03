package lamp.admin.domain.app.base.model.form;

import lamp.admin.core.app.simple.AppProcessType;
import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.domain.old.app.model.ParametersType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SimpleAppUpdateForm {

	private String name;
	private String description;

	@NotNull
	private AppResourceType resourceType;

	private String repositoryId;
	private String groupId;
	private String artifactId;
	private String artifactName;
	private String version;

	private String appUrl;

	@NotNull
	private AppProcessType processType;
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
