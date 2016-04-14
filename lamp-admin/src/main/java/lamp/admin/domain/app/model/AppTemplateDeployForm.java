package lamp.admin.domain.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AppTemplateDeployForm extends AppRegisterForm {

	private List<String> targetServerIds;
}
