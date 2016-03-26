package lamp.admin.domain.agent.model;

import lamp.admin.domain.monitoring.model.HealthStatus;
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
	private String id;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "code", column = @Column(name = "agent_status")),
			@AttributeOverride(name = "description", column = @Column(name = "agent_status_description"))
	})
	private HealthStatus agentStatus;

	@Column(name = "agent_status_date")
	private LocalDateTime agentStatusDate;

}
