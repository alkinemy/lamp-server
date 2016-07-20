package lamp.common.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Event implements Serializable {

	private long time;
	private EventLevel level;
	private String name;
	private String message;
	private Exception exception;

	private EventTarget target;

	public Event(EventLevel level, String name, String message, Exception e) {
		this(level, name, message, e, null);
	}

	public Event(EventLevel level, String name, String message, Exception e, EventTarget target) {
		this.time = System.currentTimeMillis();
		this.level = level;
		this.name = name;
		this.message = message;
		this.exception = e;
		this.target = target;
	}

}
