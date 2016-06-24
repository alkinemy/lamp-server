package lamp.admin.core.app.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HealthCheck {

	private String protocol;
	private String path;
	private int port;

	private int gracePeriodSeconds;
	private int intervalSeconds;
	private int maxConsecutiveFailures;
	private int timeoutSeconds;

}
