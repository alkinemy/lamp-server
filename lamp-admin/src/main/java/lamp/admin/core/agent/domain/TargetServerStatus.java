package lamp.admin.core.agent.domain;

import lamp.admin.core.monitoring.domain.HealthStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_target_server_status")
public class TargetServerStatus {

	@Id
	private Long id;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "code", column = @Column(name = "agent_status")),
			@AttributeOverride(name = "description", column = @Column(name = "agent_status_description"))
	})
	private HealthStatus agentStatus;

	@Column(name = "agent_status_date")
	private LocalDateTime agentStatusDate;

}
