package lamp.common.collector.model;

import java.util.Map;

@Deprecated
public interface MetricsTarget {

	String getId();
	String getName();

	String getGroupId();
	String getArtifactId();

	String getHostname();
	String getAddress();

	String getMetricsType();
	String getMetricsUrl();
	String getMetricsExportPrefix();

	Map<String, String> getTags();

	boolean isMetricsCollectionEnabled();
}
