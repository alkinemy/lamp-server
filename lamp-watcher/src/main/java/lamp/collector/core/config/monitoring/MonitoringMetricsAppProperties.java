package lamp.collector.core.config.monitoring;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "monitoring.metrics.app", ignoreUnknownFields = true)
public class MonitoringMetricsAppProperties {

	private boolean enabled;

	private long period = 5 * 1000;

}
