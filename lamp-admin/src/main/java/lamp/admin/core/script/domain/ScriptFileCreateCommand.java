package lamp.admin.core.script.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = ScriptCommandType.Values.FILE_CREATE)
@Table(name = "lamp_script_file_create_command")
@PrimaryKeyJoinColumn(name = "id")
public class ScriptFileCreateCommand extends ScriptCommand {

	private String filename;

	private String content;


	@Column(name = "readable", columnDefinition = "TINYINT", nullable = false)
	private boolean read = true;
	@Column(name = "writable",columnDefinition = "TINYINT", nullable = false)
	private boolean write = true;
	@Column(name = "executable",columnDefinition = "TINYINT", nullable = false)
	private boolean execute = false;

}
