package lamp.collector.metrics;

import lamp.collector.core.Endpoint;
import lamp.common.collector.model.CollectionTarget;
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
