package lamp.common.collector.model;

import java.util.Map;

public interface MetricsTarget {

	String getId();
	String getName();

	String getHostname();
	String getAddress();

	String getGroupId();
	String getArtifactId();
	String getVersion();

	String getMetricsType();
	String getMetricsUrl();
	String getMetricsExportPrefix();

	Map<String, String> getTags();

	boolean isMetricsCollectionEnabled();
}
