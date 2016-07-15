package lamp.collector.health;

import lamp.common.collector.model.CollectionTarget;
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
	private String status;

	public TargetHealth( Map<String, String> tags, long timestamp, String status) {
		this.timestamp = timestamp;
		this.tags = tags;
		this.status = status;
	}

}
