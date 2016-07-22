package lamp.admin.domain.host.model.entity;

import lamp.admin.core.host.ClusterType;
import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lamp.admin.domain.host.model.HostStatusCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lamp_cluster")

public class ClusterEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	private String name;
	private String description;

	@Enumerated(EnumType.STRING)
	private ClusterType type;

	private String dataType;
	private String data;

}