package lamp.admin.domain.script.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptFileRemoveCommandDto extends ScriptCommandDto {

	private String filename;

	@Override public ScriptCommandType getType() {
		return ScriptCommandType.FILE_REMOVE;
	}

}
