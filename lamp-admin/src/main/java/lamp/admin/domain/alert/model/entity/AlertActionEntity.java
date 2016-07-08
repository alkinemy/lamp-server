package lamp.admin.domain.alert.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "lamp_alert_action")
public class AlertActionEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	private String name;
	private String description;

	private String type;

	private String data;

}

