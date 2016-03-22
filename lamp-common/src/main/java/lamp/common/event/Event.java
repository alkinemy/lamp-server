package lamp.common.event;

import lamp.common.monitoring.model.Tenant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Event {

	private LocalDateTime time;
	private EventLevel level;
	private String name;
	private String message;

	private Tenant tenant;

	private Map<String, String> details;

	public Event(EventLevel level, String name, Throwable e) {
		this(level, name, e, null);
	}

	public Event(EventLevel level, String name, Throwable e, Tenant tenant) {
		this.time = LocalDateTime.now();
		this.level = level;
		this.name = name;
		this.message = e.getMessage();
		this.tenant = tenant;
	}
}
