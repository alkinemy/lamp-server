package lamp.watcher.core.config.monitoring;

import lamp.support.kairosdb.KairosdbProperties;
import lamp.watcher.core.config.ConfigConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = ConfigConstants.MONITORING_HEALTH_KAIROSDB_PREFIX, ignoreUnknownFields = true)
public class MonitoringHealthKairosdbProperties extends KairosdbProperties {

	private long period;

}
