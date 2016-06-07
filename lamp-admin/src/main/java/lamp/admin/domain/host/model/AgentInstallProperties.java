package lamp.admin.domain.host.model;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.script.model.FileCreateCommand;
import lamp.admin.domain.script.model.ScriptCommand;
import lamp.common.utils.IOUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.*;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "lamp.agent")
public class AgentInstallProperties implements ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	private String groupId = "lamp.agent";
	private String artifactId = "lamp-agent";

	private String agentInstallDirectory = "/lamp/agent/2";
	private String agentInstallFilename = "lamp-agent.jar";

	private int agentPort = 9090;
	private String agentFile = "classpath:agent/lamp-agent.jar";
	private String agentPropertiesFilePath = "classpath:agent/lamp-agent.properties";
	private String agentShellFilePath = "classpath:agent/lamp-agent.sh";

	private String startCommandLine = "./lamp-agent.sh start";
	private String stopCommandLine = "./lamp-agent.sh stop";

	public Map<String, Object> getParameters() {
		Map<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("agentPort", agentPort);
		return parameters;
	}

	public Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	public List<ScriptCommand> getInstallScriptCommands() throws IOException {
		List<ScriptCommand> scriptCommands = new ArrayList<>();
		{
			Resource resource = resourceLoader.getResource(agentPropertiesFilePath);
			String content = IOUtils.toString(resource.getInputStream(), LampAdminConstants.DEFAULT_CHARSET);

			FileCreateCommand fileCreateCommand = new FileCreateCommand();
			fileCreateCommand.setFilename("lamp-agent.properties");
			fileCreateCommand.setContent(content);

			scriptCommands.add(fileCreateCommand);
		}
		{
			Resource resource = resourceLoader.getResource(agentShellFilePath);
			String content = IOUtils.toString(resource.getInputStream(), LampAdminConstants.DEFAULT_CHARSET);

			FileCreateCommand fileCreateCommand = new FileCreateCommand();
			fileCreateCommand.setFilename("lamp-agent.sh");
			fileCreateCommand.setContent(content);
			fileCreateCommand.setExecutable(true);

			scriptCommands.add(fileCreateCommand);
		}
		return scriptCommands;
	}

	@Override public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
