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


	@Column(columnDefinition = "TINYINT", nullable = false)
	private boolean readable = true;
	@Column(columnDefinition = "TINYINT", nullable = false)
	private boolean writable = true;
	@Column(columnDefinition = "TINYINT", nullable = false)
	private boolean executable = false;

}
