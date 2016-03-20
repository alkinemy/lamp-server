package lamp.common.collector.model;

import lamp.common.monitoring.model.Tenant;

import java.util.Map;

public interface HealthTarget extends Tenant {

	String getName();

	String getHostname();
	String getAddress();

	String getHealthType();
	String getHealthUrl();
	String getHealthExportPrefix();

	Map<String, String> getTags();

	boolean isHealthCollectionEnabled();
}
