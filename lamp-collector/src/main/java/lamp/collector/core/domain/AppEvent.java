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
@Table(name = "lamp_watched_app_event")
public class AppEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String appId;
	private String appName;

	private String appHostname;

	private String agentId;
	private String artifactId;

	private String name;
	private AppEventLevel level;
	private String message;

	private LocalDateTime time;


	public static AppEvent of(TargetApp targetApp, String eventName, AppEventLevel eventLevel, Throwable t) {
		AppEvent event = new AppEvent();
		event.setAppId(targetApp.getId());
		event.setAppName(targetApp.getName());
		event.setAppHostname(targetApp.getHostname());

		event.setName(eventName);
		event.setLevel(eventLevel);
		event.setMessage(ExceptionUtils.getStackTrace(t));
		return event;
	}
}
