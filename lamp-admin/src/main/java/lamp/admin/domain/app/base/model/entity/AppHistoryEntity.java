package lamp.admin.domain.app.base.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@IdClass(AppHistoryId.class)
@Table(name = "lamp_app_history")
public class AppHistoryEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	@Id
	private String version;
	@Enumerated(EnumType.STRING)
	private AppType type;

	private String path;
	private String parentPath;
	private String name;

	private String description;

	private String clusterId;
	private long cpu;
	private long memory;
	private long diskSpace;
	private long instances;

	private String data;

}
