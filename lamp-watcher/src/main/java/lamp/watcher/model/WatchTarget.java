package lamp.watcher.model;

import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.MetricsTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class WatchTarget implements HealthTarget, MetricsTarget {

	private String id;

	private String name;
	private String description;

	private String hostname;
	private String address;

	private String targetType;

	private String groupId;
	private String artifactId;
	private String version;

	private boolean healthMonitoringEnabled = false;
	private boolean healthCollectionEnabled = false;
	private String healthType;
	private String healthUrl;
	private String healthExportPrefix;

	private boolean metricsMonitoringEnabled = false;
	private boolean metricsCollectionEnabled = false;
	private String metricsType;
	private String metricsUrl;
	private String metricsExportPrefix;

	private String tagsJsonString;

	private Map<String, String> tags;

}
