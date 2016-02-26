package lamp.admin.core.monitoring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
public class TargetMetrics {

	private long timestamp;

	private String id;
	private String name;

	private Map<String, Object> metrics;

	private Map<String, String> tags;


}
