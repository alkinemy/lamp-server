package lamp.admin.domain.app.base.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "lamp_app")
public class AppEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	private String name;
	private String description;

	private String groupId;

	private int cpu;
	private int memory;
	private int diskSpace;
	private int instances;

	private String data;

}
