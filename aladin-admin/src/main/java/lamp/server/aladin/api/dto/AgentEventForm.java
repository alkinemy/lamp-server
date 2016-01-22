package lamp.server.aladin.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AgentEventForm {

	private String eventName;

	private String eventLevel;

	private Date eventTime;

	private String appId;

	private String message;

}
