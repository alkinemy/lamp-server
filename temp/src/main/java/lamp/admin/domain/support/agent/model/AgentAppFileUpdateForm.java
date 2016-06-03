package lamp.admin.domain.support.agent.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.io.Resource;

@Getter
@Setter
@ToString
public class AgentAppFileUpdateForm {

	private String id;

	private String version;

	private Resource installFile;

}
