package lamp.admin.domain.app.base.model.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AppDeployForm {

	private List<String> hostIds;

}
