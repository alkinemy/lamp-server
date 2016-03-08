package lamp.watcher.core.config.monitoring;

import lamp.watcher.core.config.ConfigConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = ConfigConstants.MONITORING_HEALTH_COLLECTION_PREFIX, ignoreUnknownFields = true)
public class MonitoringHealthCollectionProperties {

	private boolean enabled;

	private long period = 5 * 1000;

}
