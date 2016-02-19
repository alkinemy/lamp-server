package lamp.admin.core.script.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScriptExecuteCommandDto extends ScriptCommandDto {

	private String commandShell;

	private String commandLine;

	@Override public ScriptCommandType getType() {
		return ScriptCommandType.EXECUTE;
	}
}
