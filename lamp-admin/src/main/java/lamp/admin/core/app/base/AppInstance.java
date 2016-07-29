package lamp.admin.core.app.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lamp.collector.core.health.HealthStatusCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppInstance {

	private String id;
	private String name;
	private String description;

	private String appId;
	private String appName;
	private String appVersion;

	private AppContainer appContainer;

	private String clusterId;
	private String clusterName;
	private String hostId;
	private String hostName;
	private String hostAddress;

	private String pid;
	private AppInstanceStatus status;
	private String statusMessage;
	private HealthStatusCode health;
	private String healthMessage;

	private boolean monitored;

	private boolean healthEndpointEnabled;
	private HealthEndpoint healthEndpoint;
	private boolean metricsEndpointEnabled;
	private MetricsEndpoint metricsEndpoint;

	private Map<String, String> tags;

}
