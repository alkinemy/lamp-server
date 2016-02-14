package lamp.admin.core.agent.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TargetServerDto {

	private Long id;
	private String name;
	private String description;

	private String hostname;
	private String address;

	private String authType;
	private String username;
	private String password;

	private Boolean agentInstalled;
	private String agentPath;

	private Boolean agentMonitor;
	private Long agentMonitorInterval;

	private String agentStatus;
}
