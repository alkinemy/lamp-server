package lamp.admin.domain.app.base.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "lamp_app")
public class AppEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	@Enumerated(EnumType.STRING)
	private AppType type;
	private String version;

	private String path;
	private String name;

	private String description;

	private String clusterId;
	private long cpu;
	private long memory;
	private long diskSpace;
	private long instances;

	private String data;

}
