package lamp.admin.domain.host.model;

import lamp.admin.LampAdminConstants;

import lamp.admin.core.script.ScriptCommand;
import lamp.admin.core.script.ScriptFileCreateCommand;
import lamp.common.utils.IOUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

	private String installDirectory = "/lamp/agent";
	private String installFilename = "lamp-agent.jar";

	private int port = 9090;
	private String file = "classpath*:agent/lamp-agent.jar";
	private String propertiesFilePath = "classpath*:agent/lamp-agent.properties";
	private String shellFilePath = "classpath*:agent/lamp-agent.sh";

	private String startCommandLine = "./lamp-agent.sh start";
	private String stopCommandLine = "./lamp-agent.sh stop";

	public Map<String, Object> getParameters() {
		Map<String, Object> parameters = new LinkedHashMap<>();
		return parameters;
	}

	public Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	public List<ScriptCommand> getInstallScriptCommands() throws IOException {
		List<ScriptCommand> scriptCommandEntities = new ArrayList<>();
		{
			Resource resource = resourceLoader.getResource(propertiesFilePath);
			String content = IOUtils.toString(resource.getInputStream(), LampAdminConstants.DEFAULT_CHARSET);

			ScriptFileCreateCommand fileCreateCommand = new ScriptFileCreateCommand();
			fileCreateCommand.setFilename("lamp-agent.properties");
			fileCreateCommand.setContent(content);

			scriptCommandEntities.add(fileCreateCommand);
		}
		{
			Resource resource = resourceLoader.getResource(shellFilePath);
			String content = IOUtils.toString(resource.getInputStream(), LampAdminConstants.DEFAULT_CHARSET);

			ScriptFileCreateCommand fileCreateCommand = new ScriptFileCreateCommand();
			fileCreateCommand.setFilename("lamp-agent.sh");
			fileCreateCommand.setContent(content);
			fileCreateCommand.setExecutable(true);

			scriptCommandEntities.add(fileCreateCommand);
		}
		return scriptCommandEntities;
	}

	@Override public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
