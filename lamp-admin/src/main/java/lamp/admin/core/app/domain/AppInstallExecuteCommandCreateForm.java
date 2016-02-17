package lamp.admin.core.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
public class AppInstallExecuteCommandCreateForm extends AppInstallCommandCreateForm {

	private CommandShell commandShell;

	private String commandLine;

	@Override public AppInstallCommandType getType() {
		return AppInstallCommandType.EXECUTE;
	}
}
