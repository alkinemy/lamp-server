package lamp.admin.core.app.simple;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lamp.admin.core.app.base.AppContainer;
import lamp.admin.core.app.base.AppContainerType;

import lamp.admin.core.app.simple.resource.AppResource;
import lamp.admin.core.script.ScriptCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonTypeName(AppContainerType.Names.SIMPLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleAppContainer implements AppContainer {

	private String name;

	private AppResource appResource;

	private String appDirectory;
	private String workDirectory;
	private String logDirectory;

	private String pidFile;
	private String ptql;

	private String stdOutFile;
	private String stdErrFile;

	private AppProcessType processType;

	private String commandShell;
	private String startCommandLine;
	private Long startTimeout = -1L;
	private String stopCommandLine;
	private Long stopTimeout = -1L;

	private boolean preInstalled = false;
	private String installFilename;

	private List<ScriptCommand> scriptCommands;
	private Map<String, Object> parameters;

}
