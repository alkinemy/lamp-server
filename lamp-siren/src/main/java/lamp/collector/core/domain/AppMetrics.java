package lamp.collector.core.domain;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AppMetrics {

	private long timestamp;

	private String id;
	private String name;

	private Map<String, Object> metrics;

	private Map<String, String> tags;


}
