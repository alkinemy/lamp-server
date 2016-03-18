package lamp.admin.core.agent.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AgentDto {

	private String id;
	private String name;

	private String groupId;
	private String artifactId;
	private String version;

	private String protocol;
	private String hostname;
	private String address;
	private int port = -1;

	private String secretKey;

	private Date time;

	private String healthType;
	private String healthPath;
	private String metricsType;
	private String metricsPath;

}
