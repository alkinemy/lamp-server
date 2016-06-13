package lamp.admin.domain.script.model;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lamp_script_command")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "command_type", discriminatorType = DiscriminatorType.STRING)
public class ScriptCommandEntity extends AbstractAuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "script_id")
	private ScriptEntity script;

	private int seq;
	private String name;
	private String description;

	@Column(name = "command_type", insertable = false, updatable = false)
	private String type;
}
