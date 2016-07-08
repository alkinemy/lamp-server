package lamp.common.collector.model;

import java.util.Map;

public interface HealthTarget {

	String getId();
	String getName();

	String getGroupId();
	String getArtifactId();

	String getHostname();
	String getAddress();

	String getHealthType();
	String getHealthUrl();
	String getHealthExportPrefix();

	Map<String, String> getTags();

	boolean isHealthCollectionEnabled();
}
