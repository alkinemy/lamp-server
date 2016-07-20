package lamp.collector.core.base.event;

public abstract class EventName {

	public static final String PREFIX = "lamp:collector:";

	public static final String TARGET_METRICS_EXPORT_FAILED = PREFIX + "TARGET_METRICS_EXPORT_FAILED";
	public static final String TARGET_METRICS_PROCESS_FAILED = PREFIX + "METRICS_EXPORT_FAILED";

	public static final String TARGET_HEALTH_PROCESS_FAILED = PREFIX + "HEALTH_PROCESS_FAILED";

}
