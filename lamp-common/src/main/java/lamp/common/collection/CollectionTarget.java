package lamp.common.collection;

import lamp.common.metrics.HealthTarget;
import lamp.common.metrics.MetricsTarget;

import java.util.Map;

public interface CollectionTarget extends HealthTarget, MetricsTarget {

	String getId();
	String getName();

	String getHostname();
	String getAddress();

	String getGroupId();
	String getArtifactId();
	String getVersion();

	Boolean getHealthCollectionEnabled();
	String getHealthType();
	String getHealthUrl();
	String getHealthExportPrefix();

	Boolean getMetricsCollectionEnabled();
	String getMetricsType();
	String getMetricsUrl();
	String getMetricsExportPrefix();

	Map<String, String> getTags();
}
