package lamp.server.watch.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WatchedApp {

	private String id;

	private String hostname;
	private String agentId;
	private String appId;

	private String healthType;
	private String healthUrl;
	private String metricsType;
	private String metricsUrl;


}
