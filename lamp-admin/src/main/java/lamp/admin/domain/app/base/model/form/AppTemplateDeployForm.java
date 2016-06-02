package lamp.admin.domain.app.base.model.form;

import lamp.admin.domain.old.app.model.AppDeployForm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
@ToString
public class AppTemplateDeployForm extends AppDeployForm {

	private String[] ids;

	private List<String> targetServerIds;
}
