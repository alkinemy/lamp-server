package lamp.admin.core.script;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lamp.admin.domain.script.model.ScriptCommandType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonTypeName(ScriptCommandType.Values.FILE_CREATE)
public class ScriptFileCreateCommand extends ScriptCommand {

	private String filename;

	private String content;

	private boolean readable = true;
	private boolean writable = true;
	private boolean executable = false;

}
