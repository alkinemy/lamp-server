package lamp.common.collector.model;

import lamp.common.monitoring.model.Tenant;

import java.util.Map;

public interface MetricsTarget extends Tenant {

	String getName();

	String getHostname();
	String getAddress();

	String getMetricsType();
	String getMetricsUrl();
	String getMetricsExportPrefix();

	Map<String, String> getTags();

	boolean isMetricsCollectionEnabled();
}
