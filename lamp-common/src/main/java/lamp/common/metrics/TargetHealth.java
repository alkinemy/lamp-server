package lamp.common.metrics;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TargetHealth {

	private long timestamp;

	private String id;
	private String name;

	private Map<String, Object> health;

	private Map<String, String> tags;

}
