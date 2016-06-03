package lamp.admin.domain.app.base.model.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JarAppUpdateForm extends SimpleAppUpdateForm {

	private String appFilename;

}
