package lamp.common.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Event implements Serializable {

	private long time;
	private EventLevel level;
	private String name;
	private String message;

	private EventTarget target;

	private Map<String, String> details;

	public Event(EventLevel level, String name, Throwable e) {
		this(level, name, e, null);
	}

	public Event(EventLevel level, String name, Throwable e, EventTarget target) {
		this.time = System.currentTimeMillis();
		this.level = level;
		this.name = name;
		this.message = e.getMessage();
		this.target = target;
	}

}
