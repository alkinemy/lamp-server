package lamp.admin.domain.support.agent.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
public class AgentRequestUser {

	private String id;
	private String secretKey;

}
