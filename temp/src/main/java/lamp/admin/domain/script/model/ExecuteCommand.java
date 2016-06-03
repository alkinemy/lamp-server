package lamp.admin.domain.script.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = ScriptCommandType.Values.EXECUTE)
@Table(name = "lamp_script_execute_command")
@PrimaryKeyJoinColumn(name = "id")
public class ExecuteCommand extends ScriptCommand {

	private String commandShell;

	private String commandLine;

}
