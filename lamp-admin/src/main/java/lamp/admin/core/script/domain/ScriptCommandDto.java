package lamp.admin.core.script.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class ScriptCommandDto {

	private Long id;
	private String name;
	private String description;

	public abstract ScriptCommandType getType();
}
