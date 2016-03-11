package lamp.collector.app.domain;

import lamp.common.event.Event;
import lamp.common.event.EventLevel;
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
public class JpaEvent extends Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDateTime time;
	private EventLevel level;
	private String name;
	private String message;

	private String sourceId;
	private String sourceName;
	private String sourceHostname;


}
