package lamp.collector.app.model;

import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.MetricsTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@Table(name = "lamp_collection_target")
public class CollectionTarget implements HealthTarget, MetricsTarget {

	@Id
	private String id;
	private String name;

	private String hostname;
	private String address;

	private String groupId;
	private String artifactId;
	private String version;

	@Column(columnDefinition = "TINYINT")
	private boolean healthCollectionEnabled;
	private String healthType;
	private String healthUrl;
	private String healthExportPrefix;

	@Column(columnDefinition = "TINYINT")
	private boolean metricsCollectionEnabled;
	private String metricsType;
	private String metricsUrl;
	private String metricsExportPrefix;

	@Override public Map<String, String> getTags() {
		return null;
	}
}
