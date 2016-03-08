package lamp.collector.common.domain;

import lamp.common.utils.ExceptionUtils;
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


	public static AppEvent of(MonitoringTarget watchedApp, String eventName, AppEventLevel eventLevel, Throwable t) {
		AppEvent event = new AppEvent();
		event.setAppId(watchedApp.getId());
		event.setAppName(watchedApp.getName());
		event.setAppHostname(watchedApp.getHostname());

		event.setName(eventName);
		event.setLevel(eventLevel);
		event.setMessage(ExceptionUtils.getStackTrace(t));
		return event;
	}
}
