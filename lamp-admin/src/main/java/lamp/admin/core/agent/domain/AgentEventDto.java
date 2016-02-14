package lamp.admin.core.agent.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AgentEventDto {

	private Long id;

	private String agentId;
	private Long agentInstanceId;
	private Long agentInstanceEventSequence;
	private String eventName;
	private String eventLevel;
	private LocalDateTime eventTime;

	private String appId;
	private String message;

}
