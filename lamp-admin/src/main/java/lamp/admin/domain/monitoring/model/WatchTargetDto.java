package lamp.admin.domain.monitoring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WatchTargetDto {

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

}
