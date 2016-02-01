package lamp.server.watch.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_watch_app_event")
public class AppEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(nullable = false)
	private String hostname;

	@Column(nullable = false)
	private String agentId;

	@Column(nullable = false)
	private String appId;

	private String eventName;
	private String eventLevel;
	private LocalDateTime eventTime;
	private Long eventSequence;
	private String message;

}
