package lamp.admin.domain.script.model;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lamp_script")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "script_type", discriminatorType = DiscriminatorType.STRING)
public class Script extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String description;
	private String version;

	@OneToMany(mappedBy = "script", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
	@OrderColumn(name = "seq")
	private List<ScriptCommand> commands = new ArrayList<>();

	@Transient
	public void addCommand(ScriptCommand command) {
		command.setScript(this);
		commands.add(command);
	}
}
