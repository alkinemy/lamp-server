package lamp.watcher.kairosdb.config;

import lamp.watcher.core.config.ConfigConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = ConfigConstants.MONITORING_HEALTH_PREFIX, ignoreUnknownFields = true)
public class MonitoringHealthProperties {

	private boolean enabled;

	private long interval = 5 * 1000;

}
