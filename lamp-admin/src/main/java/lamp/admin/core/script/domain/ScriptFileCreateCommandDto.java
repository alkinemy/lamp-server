package lamp.admin.core.script.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScriptFileCreateCommandDto extends ScriptCommandDto {

	private String filename;

	private String content;

	private boolean readable = true;
	private boolean writable = true;
	private boolean executable = false;

	@Override public ScriptCommandType getType() {
		return ScriptCommandType.FILE_CREATE;
	}
}
