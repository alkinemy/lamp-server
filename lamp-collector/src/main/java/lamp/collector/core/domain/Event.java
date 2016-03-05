package lamp.collector.core.domain;

import lamp.admin.utils.ExceptionUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "lamp_event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private AppEventLevel level;
	private String message;

	private LocalDateTime time;

	private String sourceId;
	private String sourceName;
	private String sourceHostname;

	private String artifactId;


	public static Event of(CollectionTarget collectionTarget, String eventName, AppEventLevel eventLevel, Throwable t) {
		Event event = new Event();
		event.setSourceId(collectionTarget.getId());
		event.setSourceName(collectionTarget.getName());
		event.setSourceHostname(collectionTarget.getHostname());

		event.setName(eventName);
		event.setLevel(eventLevel);
		event.setMessage(ExceptionUtils.getStackTrace(t));
		return event;
	}
}
