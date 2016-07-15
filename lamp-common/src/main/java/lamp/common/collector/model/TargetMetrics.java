package lamp.common.collector.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class TargetMetrics implements CollectionTarget {

	private long timestamp;

	private String id;
	private String name;

	private String groupId;
	private String artifactId;

	private Map<String, Object> metrics;

	private Map<String, String> tags;

	public TargetMetrics(long timestamp, Map<String, Object> metrics, Map<String, String> tags) {
		this.timestamp = timestamp;
		this.metrics = metrics;
		this.tags = tags;
	}

}
