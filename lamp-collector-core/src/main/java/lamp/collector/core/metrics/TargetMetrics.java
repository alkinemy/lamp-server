package lamp.collector.core.metrics;

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
public class TargetMetrics implements CollectionTarget {

	private String id;
	private Map<String, String> tags;

	private long timestamp;
	private Map<String, Object> metrics;

	private Exception exception;

	public TargetMetrics(String id, Map<String, String> tags, long timestamp, Map<String, Object> metrics) {
		this.id = id;
		this.tags = tags;
		this.timestamp = timestamp;
		this.metrics = metrics;
	}

	public TargetMetrics(String id, Map<String, String> tags, long timestamp, Exception exception) {
		this.id = id;
		this.tags = tags;
		this.timestamp = timestamp;
		this.exception = exception;
	}

	public TargetMetrics(MetricsTarget target, long timestamp, Map<String, Object> metrics) {
		this(target.getId(), target.getTags(), timestamp, metrics);
	}

	public TargetMetrics(MetricsTarget target, long timestamp, Exception exception) {
		this(target.getId(), target.getTags(), timestamp, exception);
	}

	public TargetMetrics(MetricsTarget target, Exception exception) {
		this(target.getId(), target.getTags(), System.currentTimeMillis(), exception);
	}

}
