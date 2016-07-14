package lamp.admin.core.app.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MetricsEndpoint {

	private EndpointProtocol protocol;
	private String host;
	private int port;

	private String path;

	private int gracePeriodSeconds;
	private int intervalSeconds;
	private int maxConsecutiveFailures;
	private int timeoutSeconds;

}
