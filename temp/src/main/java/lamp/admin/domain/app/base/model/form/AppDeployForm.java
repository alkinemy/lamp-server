package lamp.admin.domain.app.base.model.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class AppDeployForm {

	@NotNull
	private String appId;

	private String instanceId;
	private String[] instanceIds;

	private List<String> targetServerIds;
}
