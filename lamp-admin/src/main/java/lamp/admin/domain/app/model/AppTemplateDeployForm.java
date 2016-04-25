package lamp.admin.domain.app.model;

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
