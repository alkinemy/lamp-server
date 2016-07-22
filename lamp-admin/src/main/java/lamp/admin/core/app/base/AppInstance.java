package lamp.admin.core.app.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
	private String appVersion;

	private String clusterId;
	private String hostId;
	private String hostName;
	private String hostAddress;

	private String pid;
	private AppInstanceStatus status;
	private String statusMessage;

	private boolean monitored;

	private boolean healthEndpointEnabled;
	private HealthEndpoint healthEndpoint;
	private boolean metricsEndpointEnabled;
	private MetricsEndpoint metricsEndpoint;

	private Map<String, String> tags;

}
