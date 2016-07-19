package lamp.admin.core.app.base;

import lamp.collector.core.base.Endpoint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class MetricsEndpoint extends Endpoint {

	private int intervalSeconds;


}
