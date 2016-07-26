package lamp.admin.domain.base.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lamp.admin.domain.base.model.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lamp_task")
public class TaskEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	@Version
	private int version;

	@Column(updatable = false)
	private String dataType;
	@Column(updatable = false)
	private String data;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;


}
