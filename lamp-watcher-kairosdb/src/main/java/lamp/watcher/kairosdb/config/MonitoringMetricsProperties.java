package lamp.watcher.kairosdb.config;

import lamp.watcher.core.config.ConfigConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = ConfigConstants.MONITORING_METRICS_PREFIX, ignoreUnknownFields = true)
public class MonitoringMetricsProperties {

	private boolean enabled;

	private long interval = 5 * 1000;

}
