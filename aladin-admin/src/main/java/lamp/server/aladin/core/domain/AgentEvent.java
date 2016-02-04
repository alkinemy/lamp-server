package lamp.server.aladin.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_agent_event")
public class AgentEvent extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String agentId;
	private Long agentInstanceId;
	private Long agentInstanceEventSequence;
	@Column(nullable = false)
	private String eventName;
	@Column(nullable = false)
	private String eventLevel;
	private LocalDateTime eventTime;

	private String appId;
	private String message;

}
