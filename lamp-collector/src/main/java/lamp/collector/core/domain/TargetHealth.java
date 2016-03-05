package lamp.collector.core.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Map;

@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TargetHealth {

	private long timestamp;

	private String id;
	private String name;

	private Map<String, Object> health;

	private Map<String, String> tags;

}
