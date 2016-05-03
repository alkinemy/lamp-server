package lamp.collector.core.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MetricsAndTags {

	private long timestamp;

	private Map<String, Object> metrics;

	private Map<String, String> tags;

	public MetricsAndTags(Map<String, Object> metrics, Map<String, String> tags) {
		this.metrics = metrics;
		this.tags = tags;
	}
}
