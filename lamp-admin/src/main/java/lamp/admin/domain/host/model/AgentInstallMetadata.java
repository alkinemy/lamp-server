package lamp.admin.domain.host.model;

import lamp.admin.core.host.HostCredentials;
import lamp.admin.core.script.ScriptCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AgentInstallMetadata {

	private String address;
	private HostCredentials hostCredentials;
	private HostConfiguration hostConfiguration;

	private String jdkFile;
	private String jdkInstallDirectory;
	private List<ScriptCommand> jdkInstallScriptCommands;


	private String agentId;
	private String agentFile;
	private String agentInstallDirectory;
	private String agentInstallFilename;
	private List<ScriptCommand> agentInstallScriptCommands;




}
