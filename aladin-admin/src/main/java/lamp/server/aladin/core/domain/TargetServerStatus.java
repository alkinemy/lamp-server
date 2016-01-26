package lamp.server.aladin.core.domain;

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
	@Column(name = "agent_status")
	private String agentStatus;

	@Column(name = "agent_status_date")
	private LocalDateTime agentStatusDate;

}
