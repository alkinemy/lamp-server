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
	private String id;

	@Column(name = "agent_id", nullable = false)
	private String agentId;

	private String eventName;
	private String eventLevel;
	private LocalDateTime eventTime;
	private Long eventSequence;
	private String appId;
	private String message;

}
