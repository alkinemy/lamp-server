package lamp.collector.core.health;

import lamp.collector.core.base.CollectionTarget;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TargetHealth implements CollectionTarget {

	private String id;

	private Map<String, String> tags;
	private long timestamp;
	private HealthStatus health;

	private Exception exception;

	public TargetHealth(String id, Map<String, String> tags, long timestamp, HealthStatus health) {
		this.id = id;
		this.tags = tags;
		this.timestamp = timestamp;
		this.health = health;
	}

	public TargetHealth(String id, Map<String, String> tags, long timestamp, Exception exception) {
		this.id = id;
		this.tags = tags;
		this.timestamp = timestamp;
		this.exception = exception;
	}

	public TargetHealth(HealthTarget target, long timestamp, HealthStatus health) {
		this(target.getId(), target.getTags(), timestamp, health);
	}

	public TargetHealth(HealthTarget target, long timestamp, Exception exception) {
		this(target.getId(), target.getTags(), timestamp, exception);
	}

	public TargetHealth(HealthTarget target, Exception exception) {
		this(target.getId(), target.getTags(), System.currentTimeMillis(), exception);
	}

	public TargetHealth(Map<String, String> tags, long timestamp, HealthStatus health) {
		this.timestamp = timestamp;
		this.tags = tags;
		this.health = health;
	}

}
