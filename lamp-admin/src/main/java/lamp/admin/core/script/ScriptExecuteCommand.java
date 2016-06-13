package lamp.admin.core.script;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lamp.admin.domain.script.model.ScriptCommandType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonTypeName(ScriptCommandType.Values.EXECUTE)
public class ScriptExecuteCommand extends ScriptCommand {

	private String commandShell;

	private String commandLine;

}
