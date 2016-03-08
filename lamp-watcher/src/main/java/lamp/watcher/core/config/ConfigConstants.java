package lamp.watcher.core.config;

public abstract class ConfigConstants {

	public static final String MONITORING_HEALTH_PREFIX = "monitoring.health";
	public static final String MONITORING_HEALTH_COLLECTION_PREFIX = MONITORING_HEALTH_PREFIX + ".collection";
	public static final String MONITORING_HEALTH_KAFKA_PREFIX = MONITORING_HEALTH_PREFIX + ".kafka";
	public static final String MONITORING_HEALTH_KAIROSDB_PREFIX = MONITORING_HEALTH_PREFIX + ".kairosdb";

	public static final String MONITORING_METRICS_PREFIX = "monitoring.metrics";
	public static final String MONITORING_METRICS_COLLECTION_PREFIX = MONITORING_METRICS_PREFIX + ".collection";
	public static final String MONITORING_METRICS_KAFKA_PREFIX = MONITORING_METRICS_PREFIX + ".kafka";
	public static final String MONITORING_METRICS_KAIROSDB_PREFIX = MONITORING_METRICS_PREFIX + ".kairosdb";

}
