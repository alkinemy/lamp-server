package lamp.admin.core.script.domain;

import lamp.admin.core.app.domain.CommandShell;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScriptExecuteCommandDto extends ScriptCommandDto {

	private CommandShell commandShell;

	private String commandLine;

	@Override public ScriptCommandType getType() {
		return ScriptCommandType.EXECUTE;
	}
}
