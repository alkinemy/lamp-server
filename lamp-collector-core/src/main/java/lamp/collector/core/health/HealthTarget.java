package lamp.collector.core.health;

import lamp.collector.core.base.Endpoint;
import lamp.collector.core.base.CollectionTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class HealthTarget implements CollectionTarget {

	private String id;

	private Endpoint endpoint;

	private Map<String, String> tags;

}
