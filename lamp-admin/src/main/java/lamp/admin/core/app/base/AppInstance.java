package lamp.admin.core.app.base;

import lamp.admin.core.host.Host;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AppInstance {

	private String id;
	private String name;
	private String description;

	private String appId;
	private String appVersion;
	private String hostId;

	private String pid;
	private AppInstanceStatus status;
	private String statusMessage;

	private boolean monitored;

	private boolean healthEndpointEnabled;
	private HealthEndpoint healthEndpoint;
	private boolean metricsEndpointEnabled;
	private MetricsEndpoint metricsEndpoint;

	private Map<String, String> tags;

	//
	private App app;
	private Host host;


}
