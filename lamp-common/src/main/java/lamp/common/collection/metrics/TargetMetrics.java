package lamp.common.collection.metrics;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TargetMetrics {

	private long timestamp;

	private String id;
	private String name;

	private Map<String, Object> metrics;

	private Map<String, String> tags;

}
