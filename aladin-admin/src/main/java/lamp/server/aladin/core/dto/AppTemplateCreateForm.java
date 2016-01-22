package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.AppResourceType;
import lamp.server.aladin.core.domain.AppProcessType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AppTemplateCreateForm {

	private Long id;
	private String name;
	private String description;

	@NotNull
	private AppResourceType resourceType = AppResourceType.NONE;

	private Long repositoryId;
	private String appGroupId;
	private String appId;
	private String appName;
	private String appVersion;
	private String appUrl;

	private AppProcessType processType = AppProcessType.DEFAULT;
	private String appDirectory;
	private String workDirectory;
	private String pidFile;

	private String commandShell;
	private String startCommandLine;
	private String stopCommandLine;

	private String appFilename;

	private boolean monitor;

	private String commands;



}
