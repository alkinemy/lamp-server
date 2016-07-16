package lamp.collector.core.metrics;

import lamp.collector.core.base.Endpoint;
import lamp.collector.core.base.CollectionTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class MetricsTarget implements CollectionTarget {

	private String id;

	private Endpoint endpoint;

	private Map<String, String> tags;

}
