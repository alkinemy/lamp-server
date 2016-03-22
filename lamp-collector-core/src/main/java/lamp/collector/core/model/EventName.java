package lamp.collector.core.model;

public abstract class EventName {

	public static final String HEALTH_LOAD_FAILED = "HEALTH_LOAD_FAILED";
	public static final String HEALTH_PROCESS_FAILED = "HEALTH_PROCESS_FAILED";
	public static final String HEALTH_EXPORT_TO_KAFKA_FAILED = "HEALTH_EXPORT_TO_KAFKA_FAILED";

	public static final String METRICS_LOAD_FAILED = "METRICS_LOAD_FAILED";
	public static final String METRICS_EXPORT_FAILED = "METRICS_EXPORT_FAILED";

	public static final String METRICS_EXPORT_TO_KAFKA_FAILED = "METRICS_EXPORT_TO_KAFKA_FAILED";
	public static final String METRICS_EXPORT_TO_KAIROSDB_FAILED = "METRICS_EXPORT_TO_KAIROSDB_FAILED";


}
