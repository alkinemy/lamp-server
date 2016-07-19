package lamp.admin.domain.app.base.model.entity;

import lamp.admin.core.app.base.AppInstanceStatus;
import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "lamp_app_instance")
public class AppInstanceEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	private String name;
	private String description;

	private String appId;
	private String appVersion;
	private String hostId;

	private String pid;
	@Enumerated(EnumType.STRING)
	private AppInstanceStatus status;
	private String statusMessage;

	private String data;
	private boolean monitored;

}
