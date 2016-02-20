package lamp.admin.core.script.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = ScriptCommandType.Values.FILE_REMOVE)
@Table(name = "lamp_script_file_remove_command")
@PrimaryKeyJoinColumn(name = "id")
public class ScriptFileRemoveCommand extends ScriptCommand {

	private String filename;

}
