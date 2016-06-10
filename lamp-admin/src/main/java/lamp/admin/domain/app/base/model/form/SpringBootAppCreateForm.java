package lamp.admin.domain.app.base.model.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpringBootAppCreateForm extends SimpleAppCreateForm {

	private String jvmOpts = "-Xms256m -Xmx2048m";
	private String springOpts;


}
