package lamp.watcher.core.config.monitoring;

import lamp.watcher.core.config.ConfigConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = ConfigConstants.MONITORING_METRICS_COLLECTION_PREFIX, ignoreUnknownFields = true)
public class MonitoringMetricsCollectionProperties {

	private boolean enabled;

	private long period = 5 * 1000;

}
