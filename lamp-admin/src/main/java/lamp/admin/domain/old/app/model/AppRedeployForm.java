package lamp.admin.domain.old.app.model;

import lamp.admin.domain.old.app.model.AppDeployForm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AppRedeployForm extends AppDeployForm {

	private boolean forceStop;

}
