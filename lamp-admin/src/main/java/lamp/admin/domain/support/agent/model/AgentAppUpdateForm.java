package lamp.admin.domain.support.agent.model;

import lamp.admin.domain.agent.model.AgentRegisterForm;
import lamp.admin.domain.app.model.AppProcessType;
import lamp.admin.domain.app.model.ParametersType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.io.Resource;

@Getter
@Setter
@ToString
public class AgentAppUpdateForm extends AgentRegisterForm{

	private boolean forceStop;

}
