package lamp.common.collector.model;

import lamp.common.monitoring.model.Tenant;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TargetHealth implements CollectionTarget {

	private long timestamp;

	private String id;
	private String name;

	private String groupId;
	private String artifactId;

	private Map<String, Object> health;

	private Map<String, String> tags;

}
