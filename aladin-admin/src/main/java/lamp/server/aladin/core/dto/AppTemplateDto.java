package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.AppProcessType;
import lamp.server.aladin.core.domain.AppResourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppTemplateDto {

	private Long id;
	private String name;
	private String description;

	private AppResourceType resourceType;

	private String appGroupId;
	private String appId;
	private String appName;
	private String appVersion;

	private AppProcessType processType;
	private String homeDirectory;
	private String workDirectory;
	private String pidFile;

	private String startCommandLine;
	private String stopCommandLine;

	private boolean preInstalled;
	private String appFilename;

	private boolean monitor;

	private String commands;



}
