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
public class AppHealth {

	private long timestamp;

	private String id;
	private String name;

	private AppHealthStatus status;
	private String description;
	private Map<String, Object> details;

	private Map<String, String> tags;

	public static AppHealth of(String id, String name, String code, String description, Map<String, Object> details) {
		return of(System.currentTimeMillis(), id, name, AppHealthStatus.valueOf(code), description, details, null);
	}

}
