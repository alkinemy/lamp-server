package lamp.server.aladin.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "lamp.agent")
public class AgentProperties {

	private String installPath;
	private String startCommandLine;

}
