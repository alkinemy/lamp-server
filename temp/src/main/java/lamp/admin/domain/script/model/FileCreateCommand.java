package lamp.admin.domain.script.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = ScriptCommandType.Values.FILE_CREATE)
@Table(name = "lamp_script_file_create_command")
@PrimaryKeyJoinColumn(name = "id")
public class FileCreateCommand extends ScriptCommand {

	@Column(length = 255)
	private String filename;
	@Column(length = 10000)
	private String content;
	private String charset;
	@Enumerated(EnumType.STRING)
	private ExpressionParser expressionParser = ExpressionParser.SPEL;


	@Column(columnDefinition = "TINYINT", nullable = false)
	private boolean readable = true;
	@Column(columnDefinition = "TINYINT", nullable = false)
	private boolean writable = true;
	@Column(columnDefinition = "TINYINT", nullable = false)
	private boolean executable = false;

}
