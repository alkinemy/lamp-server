package lamp.server.watch.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AppEventForm {

	private String id;

	private String hostname;
	private String agentId;
	private String appId;

	private String eventName;
	private String eventLevel;
	private Date eventTime;
	private Long eventSequence;
	private String message;

}
