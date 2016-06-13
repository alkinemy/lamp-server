package lamp.admin.core.script;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lamp.admin.domain.script.model.ScriptCommandType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName(ScriptCommandType.Values.FILE_REMOVE)
public class ScriptFileRemoveCommand extends ScriptCommand {

	private String filename;

}
