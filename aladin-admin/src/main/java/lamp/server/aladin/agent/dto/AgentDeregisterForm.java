package lamp.server.aladin.agent.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class AgentDeregisterForm {

	@NotEmpty
	private String id;

	private String secretKey;

}
