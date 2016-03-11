package lamp.common.metrics;

import java.util.Map;

public interface HealthTarget {

	String getId();
	String getName();

	String getHostname();
	String getAddress();

	String getHealthType();
	String getHealthUrl();
	String getHealthExportPrefix();

	Map<String, String> getTags();
}
