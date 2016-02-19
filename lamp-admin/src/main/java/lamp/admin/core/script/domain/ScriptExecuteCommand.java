package lamp.admin.core.script.domain;

import lamp.admin.core.app.domain.CommandShell;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = ScriptCommandType.Values.EXECUTE)
@Table(name = "lamp_script_execute_command")
@PrimaryKeyJoinColumn(name = "id")
public class ScriptExecuteCommand extends ScriptCommand {

	@Enumerated(EnumType.STRING)
	private CommandShell commandShell;

	private String commandLine;

}
