package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppInstallExecuteCommandUpdateForm extends AppInstallCommandUpdateForm {

	private CommandShell commandShell;

	private String commandLine;

	@Override public String getType() {
		return "execute";
	}
}
