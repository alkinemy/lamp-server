package lamp.watcher.kairosdb.config;

import lamp.support.kairosdb.SimpleKairosdbProperties;
import lamp.watcher.core.config.ConfigConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = ConfigConstants.MONITORING_KAIROSDB_PREFIX, ignoreUnknownFields = true)
public class MonitoringMetricsKairosdbProperties extends SimpleKairosdbProperties {

	private long interval;

}
