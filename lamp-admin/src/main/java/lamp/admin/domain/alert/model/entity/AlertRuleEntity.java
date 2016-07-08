package lamp.admin.domain.alert.model.entity;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.utils.StringUtils;
import lamp.monitoring.core.health.model.MonitoringHealthTarget;
import lamp.monitoring.core.metrics.model.MonitoringAlertTarget;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "lamp_alert_rule")
public class AlertRuleEntity extends AbstractAuditingEntity {

	@Id
	private String id;
	private String name;
	private String description;

	private String type;

	private String data;

}
