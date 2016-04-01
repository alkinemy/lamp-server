package lamp.admin.domain.monitoring.model;

import lamp.admin.domain.base.model.AbstractAuditingEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.utils.StringUtils;
import lamp.monitoring.core.health.model.MonitoringHealthTarget;
import lamp.monitoring.core.metrics.model.MonitoringMetricsTarget;
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
@Table(name = "lamp_watch_target")
public class WatchTarget extends AbstractAuditingEntity implements HealthTarget, MetricsTarget, MonitoringHealthTarget, MonitoringMetricsTarget {

	@Id
	private String id;

	private String name;
	private String description;

	private String hostname;
	private String address;

	private String targetType;

	private String groupId;
	private String artifactId;
	private String version;

	@Column(columnDefinition = "TINYINT")
	private boolean healthMonitoringEnabled = false;
	@Column(columnDefinition = "TINYINT")
	private boolean healthCollectionEnabled = false;
	private String healthType;
	private String healthUrl;
	private String healthExportPrefix;

	@Column(columnDefinition = "TINYINT")
	private boolean metricsMonitoringEnabled = false;
	@Column(columnDefinition = "TINYINT")
	private boolean metricsCollectionEnabled = false;
	private String metricsType;
	private String metricsUrl;
	private String metricsExportPrefix;

	@Column(name = "tags")
	private String tagsJsonString;

	@Override public Map<String, String> getTags() {
		if (StringUtils.isNotBlank(tagsJsonString)) {
			return JsonUtils.parse(tagsJsonString, LinkedHashMap.class);
		}
		return Collections.emptyMap();
	}

}
